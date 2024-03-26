package com.example.edumate.activities

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edumate.R
import com.example.edumate.adapters.PlacementSeriesParentAdapter
import com.example.edumate.models.PlacementChildItem
import com.example.edumate.models.PlacementParentItem
import com.example.edumate.models.StudyPlaylist
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class PlacementSeriesActivity : AppCompatActivity() {

    private var placementSeriesParentList = mutableListOf<PlacementParentItem>()
    private lateinit var adapter: PlacementSeriesParentAdapter
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placement_series)
        firestore = FirebaseFirestore.getInstance()

        setUpViews()
    }

    private fun setUpViews() {
        setUpPlacementSeriesList()
        setUpRecyclerView()
        setUpToolbar()
        setUpSearchView()
    }

    private fun setUpSearchView() {
        val searchView: androidx.appcompat.widget.SearchView = findViewById(R.id.placementSearchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }

    // Filtering the list using search View
    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = mutableListOf<PlacementParentItem>()
            for (i in placementSeriesParentList) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this@PlacementSeriesActivity, "No data found", Toast.LENGTH_SHORT)
                    .show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun setUpToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.placementSeriesToolbar)
        toolbar?.title = "Placement Series"
        setSupportActionBar(toolbar)

        toolbar?.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setUpPlacementSeriesList() {
        val collectionReference = firestore.collection("Placement Series")

        collectionReference.get()
            .addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot.documents) {
                    val companyId = document.id
                    val childItemList = mutableListOf<PlacementChildItem>()

                    collectionReference.document(companyId).collection("company")
                        .get()
                        .addOnSuccessListener { childQuerySnapshot ->
                            childItemList.clear()
                            childItemList.addAll(childQuerySnapshot.toObjects(PlacementChildItem::class.java))

                            val existingParentItemIndex =
                                placementSeriesParentList.indexOfFirst { it.title == companyId }
                            if (existingParentItemIndex != -1) {
                                placementSeriesParentList[existingParentItemIndex] =
                                    PlacementParentItem(
                                        companyId,
                                        R.drawable.company,
                                        childItemList
                                    )
                            } else {
                                placementSeriesParentList.add(
                                    PlacementParentItem(
                                        companyId,
                                        R.drawable.company,
                                        childItemList
                                    )
                                )
                            }
                            adapter.notifyDataSetChanged()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
    }


    private fun setUpRecyclerView() {

        val placementSeriesRecyclerView =
            findViewById<RecyclerView>(R.id.placementSeriesParentRecyclerView)
        adapter = PlacementSeriesParentAdapter(this, placementSeriesParentList)
        placementSeriesRecyclerView.layoutManager = LinearLayoutManager(this)
        placementSeriesRecyclerView.adapter = adapter
    }
}