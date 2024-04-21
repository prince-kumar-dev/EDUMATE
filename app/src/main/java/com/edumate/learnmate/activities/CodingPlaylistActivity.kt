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

class CodingPlaylistActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private var codingPlaylistList = mutableListOf<StudyPlaylist>()
    private lateinit var adapter: StudyPlaylistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coding_playlist)
        firestore = FirebaseFirestore.getInstance()

        setUpViews() // Call method to set up views
    }

    private fun setUpViews() {
        setUpToolbar() // Set up toolbar
        setUpStudyPlayList() // Fetch coding playlist data from Firestore
        setUpRecyclerView() // Set up RecyclerView to display coding playlists
        setUpSearchView() // Set up SearchView for filtering the playlist
    }

    // Set up SearchView for filtering the playlist
    private fun setUpSearchView() {
        val searchView: androidx.appcompat.widget.SearchView = findViewById(R.id.playlistSearchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText) // Filter the list based on the query
                return true
            }
        })
    }

    // Filtering the list using search View
    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = mutableListOf<StudyPlaylist>()
            for (i in codingPlaylistList) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                // Show a toast message if no data is found
                Toast.makeText(this@CodingPlaylistActivity, "No data found", Toast.LENGTH_SHORT)
                    .show()
            } else {
                adapter.setFilteredList(filteredList) // Update RecyclerView with filtered list
            }
        }
    }

    // Fetch coding playlist data from Firestore
    private fun setUpStudyPlayList() {
        val collectionReference = firestore.collection("Coding Playlist")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                // Show toast message in case of error fetching data
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            // Clear the list and populate it with fetched data
            codingPlaylistList.clear()
            codingPlaylistList.addAll(value.toObjects(StudyPlaylist::class.java))
            adapter.notifyDataSetChanged() // Notify adapter about the data change
        }
    }

    // Set up RecyclerView to display coding playlists
    private fun setUpRecyclerView() {
        val studyPlaylistRecyclerView = findViewById<RecyclerView>(R.id.codingPlaylistRecyclerView)
        adapter = StudyPlaylistAdapter(this, codingPlaylistList)
        studyPlaylistRecyclerView.layoutManager = LinearLayoutManager(this)
        studyPlaylistRecyclerView.adapter = adapter
    }

    // Set up toolbar
    private fun setUpToolbar() {
        val codingPlaylistTitle = findViewById<com.google.android.material.textview.MaterialTextView>(R.id.codingPlaylistTitle)
        val backArrowImg = findViewById<ImageView>(R.id.backArrowImg)
        codingPlaylistTitle.text = "Coding Playlist"

        backArrowImg.setOnClickListener {
            finish()
        }
    }
}
