package com.edumate.learnmate.fragments

import com.edumate.learnmate.adapters.BlogsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edumate.learnmate.R
import com.edumate.learnmate.models.Blogs
import com.edumate.learnmate.models.Department
import com.edumate.learnmate.models.StudyPlaylist
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class BlogsList : Fragment() {

    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var firestore: FirebaseFirestore
    private var blogList = mutableListOf<Blogs>()
    private lateinit var  blogAdapter: BlogsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firestore = FirebaseFirestore.getInstance()
        val rootView = inflater.inflate(R.layout.blogs_article_list, container, false)

        // Initialize view

        setUpViews(rootView)
        return rootView
    }

    private fun setUpViews(view: View) {
        setUpRecyclerView(view)
        setUpBlogList(view)
        setUpSearchView(view)
    }

    private fun setUpBlogList(view: View) {

        val collectionReference = firestore.collection("Blogs")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            blogList.clear()
            blogList.addAll(value.toObjects(Blogs::class.java))
            blogAdapter.notifyDataSetChanged()
        }
    }

    private fun setUpSearchView(view: View) {
        val searchView: SearchView = view.findViewById(R.id.articleSearchView)

        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText) // Filter the list based on the query
                return true
            }
        })
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = mutableListOf<Blogs>()
            for (i in blogList) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                // Show a toast message if no data is found
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT)
                    .show()
            } else {
                blogAdapter.setFilteredList(filteredList) // Update RecyclerView with filtered list
            }
        }
    }

    private fun setUpRecyclerView(view: View) {
        // Set up RecyclerView
        articlesRecyclerView = view.findViewById(R.id.articlesRecyclerView)

        val layoutManager = LinearLayoutManager(requireContext())
        blogAdapter = BlogsAdapter(blogList)
        articlesRecyclerView.layoutManager = layoutManager
        articlesRecyclerView.adapter = blogAdapter
    }
}