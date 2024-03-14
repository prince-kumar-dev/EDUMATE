package com.example.edumate.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edumate.R
import com.example.edumate.adapters.PlacementSeriesParentAdapter
import com.example.edumate.models.PlacementChildItem
import com.example.edumate.models.PlacementParentItem
import com.google.firebase.firestore.FirebaseFirestore

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

        collectionReference.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                val placementSeriesChildList = mutableListOf<PlacementChildItem>()

                collectionReference.document(document.id).collection("company")
                    .addSnapshotListener { value, error ->
                        if (value == null || error != null) {
                            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                            return@addSnapshotListener
                        }
                        placementSeriesChildList.clear()
                        placementSeriesChildList.addAll(value.toObjects(PlacementChildItem::class.java))

                        if (placementSeriesParentList.contains(
                                PlacementParentItem(
                                    document.id,
                                    R.drawable.company,
                                    placementSeriesChildList
                                )
                            )
                        ) {
                            placementSeriesParentList.remove(
                                PlacementParentItem(
                                    document.id,
                                    R.drawable.company,
                                    placementSeriesChildList
                                )
                            )
                        }
                        placementSeriesParentList.add(
                            PlacementParentItem(
                                document.id,
                                R.drawable.company,
                                placementSeriesChildList
                            )
                        )
                        adapter.notifyDataSetChanged()
                    }
            }
        }.addOnFailureListener { exception ->
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