package com.edumate.fellowmate.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edumate.fellowmate.activities.CompleteCoursePackage
import com.edumate.fellowmate.adapters.CoursesListAdapter
import com.edumate.fellowmate.adapters.RecommendedCourseAdapter
import com.edumate.fellowmate.models.CoursesList
import com.edumate.fellowmate.models.RecommendedCourse
import com.edumate.fellowmate.R
import com.google.firebase.firestore.FirebaseFirestore

class Courses : Fragment() {

    private lateinit var firestore: FirebaseFirestore
    private var coursesList = mutableListOf<CoursesList>()
    private var recommendedCourseList = mutableListOf<RecommendedCourse>()
    private lateinit var courseAdapter: CoursesListAdapter
    private lateinit var recommendedCourseAdapter: RecommendedCourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firestore = FirebaseFirestore.getInstance()

        val view = inflater.inflate(R.layout.fragment_courses, container, false)

        setUpViews(view)
        return view
    }

    private fun setUpViews(view: View) {
        setUpCoursesList()
        // setUpRecommendedCourse()
        setUpCoursesRecyclerView(view)
        // setUpRecommendedCourseRecyclerView(view)
        setUpSearchView(view)
        setUpButtons(view)
    }

    private fun setUpButtons(view: View) {
        val chaiAurCodeFreeCourses = view.findViewById<Button>(R.id.chaiAurCodeFreeCourses)
        val sdeCompletePackage = view.findViewById<Button>(R.id.sdeBtn)

        sdeCompletePackage.setOnClickListener {
            val intent = Intent(context, CompleteCoursePackage::class.java)
            intent.putExtra("TITLE", "SDE Complete Package")
            startActivity(intent)
        }

        chaiAurCodeFreeCourses.setOnClickListener {
            val url = "https://courses.chaicode.com/learn"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun setUpSearchView(view: View) {
        val searchView: SearchView = view.findViewById(R.id.coursesSearchView)

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
        if(query != null) {
            val filteredList = mutableListOf<CoursesList>()
            for (i in coursesList) {
                if (i.title.lowercase().contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                // Show a toast message if no data is found
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT)
                    .show()
            } else {
                courseAdapter.setFilteredList(filteredList) // Update RecyclerView with filtered list
            }
        }
    }

//    private fun setUpRecommendedCourseRecyclerView(view: View) {
//        val recommendedCoursesRecyclerView =
//            view.findViewById<RecyclerView>(R.id.recommendedCoursesRecyclerView)
//
//        if (context != null) {
//            recommendedCourseAdapter = RecommendedCourseAdapter(requireContext(), recommendedCourseList)
//            recommendedCoursesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            recommendedCoursesRecyclerView.adapter = recommendedCourseAdapter
//        } else {
//            Toast.makeText(context, "Context is null", Toast.LENGTH_SHORT).show()
//        }
//    }

//    private fun setUpRecommendedCourse() {
//        val collectionReference = firestore.collection("Recommended Course")
//        collectionReference.addSnapshotListener { value, error ->
//            if (error != null) {
//                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
//                return@addSnapshotListener
//            }
//            if (value == null || value.isEmpty) {
//                Toast.makeText(context, "No courses found", Toast.LENGTH_SHORT).show()
//                return@addSnapshotListener
//            }
//            recommendedCourseList.clear()
//            recommendedCourseList.addAll(value.toObjects(RecommendedCourse::class.java))
//            recommendedCourseAdapter.notifyDataSetChanged()
//        }
//    }

    private fun setUpCoursesRecyclerView(view: View) {
        val coursesRecyclerView = view.findViewById<RecyclerView>(R.id.coursesRecyclerView)

        if (context != null) {
            courseAdapter = CoursesListAdapter(requireContext(), coursesList)
            coursesRecyclerView.layoutManager = GridLayoutManager(context, 2)
            coursesRecyclerView.adapter = courseAdapter
        } else {
            Toast.makeText(context, "Context is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpCoursesList() {
        val collectionReference = firestore.collection("Courses")
        collectionReference.addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            if (value == null || value.isEmpty) {
                Toast.makeText(context, "No courses found", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            coursesList.clear()
            coursesList.addAll(value.toObjects(CoursesList::class.java))
            courseAdapter.notifyDataSetChanged()
        }
    }
}