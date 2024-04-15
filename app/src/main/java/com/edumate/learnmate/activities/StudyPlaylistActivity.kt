package com.edumate.learnmate.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edumate.learnmate.R
import com.edumate.learnmate.adapters.StudyPlaylistAdapter
import com.edumate.learnmate.models.StudyPlaylist
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class StudyPlaylistActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private var studyPlaylistList = mutableListOf<StudyPlaylist>()
    private lateinit var adapter: StudyPlaylistAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_playlist)
        firestore = FirebaseFirestore.getInstance()

        setUpViews()
    }

    private fun setUpViews() {
        setUpToolbar()
        setUpStudyPlayList()
        setUpRecyclerView()
        setUpSearchView()
    }

    // Search View Setup
    private fun setUpSearchView() {
        val searchView: androidx.appcompat.widget.SearchView = findViewById(R.id.playlistSearchView)

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
            val filteredList = mutableListOf<StudyPlaylist>()
            for (i in studyPlaylistList) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this@StudyPlaylistActivity, "No data found", Toast.LENGTH_SHORT)
                    .show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun setUpStudyPlayList() {
        val collectionReference = firestore.collection("Study Playlist")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            studyPlaylistList.clear()
            studyPlaylistList.addAll(value.toObjects(StudyPlaylist::class.java))
            adapter.notifyDataSetChanged()

        }
    }


    private fun setUpRecyclerView() {
        val studyPlaylistRecyclerView = findViewById<RecyclerView>(R.id.studyPlaylistRecyclerView)
        adapter = StudyPlaylistAdapter(this, studyPlaylistList)
        studyPlaylistRecyclerView.layoutManager = LinearLayoutManager(this)
        studyPlaylistRecyclerView.adapter = adapter
    }

    private fun setUpToolbar() {
        val studyPlaylistTitle = findViewById<com.google.android.material.textview.MaterialTextView>(R.id.studyPlaylistTitle)
        val backArrowImg = findViewById<ImageView>(R.id.backArrowImg)
        studyPlaylistTitle.text = "Study Playlist"

        backArrowImg.setOnClickListener {
            finish()
        }
    }
}