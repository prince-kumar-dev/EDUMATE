package com.edumate.fellowmate.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edumate.fellowmate.R
import com.edumate.fellowmate.adapters.CourseAdapter
import com.edumate.fellowmate.models.Course
import com.google.firebase.firestore.FirebaseFirestore

class Course : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private var courseList = mutableListOf<Course>()
    private lateinit var courseAdapter: CourseAdapter
    private var courseCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        courseCategory = intent.getStringExtra("TITLE")

        setUpViews()
    }

    private fun setUpViews() {
        setUpToolbar()
        setUpRecyclerView()
        setUpCourseList()
    }

    private fun setUpRecyclerView() {
        val courseRecyclerView = findViewById<RecyclerView>(R.id.courseRecyclerView)
        courseAdapter = CourseAdapter(this, courseList)
        courseRecyclerView.layoutManager = GridLayoutManager(this, 1)
        courseRecyclerView.adapter = courseAdapter
    }

    private fun setUpCourseList() {
        if(courseCategory != null) {
            val collectionReference = firestore.collection("Courses")

            collectionReference.document(courseCategory!!).collection("courseList")
                .addSnapshotListener { value, error ->
                    if (value == null || error != null) {
                        Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }

                    courseList.clear()
                    courseList.addAll(value.toObjects(Course::class.java))
                    courseAdapter.notifyDataSetChanged()
                }
        }
    }

    private fun setUpToolbar() {
        val courseTitle = findViewById<com.google.android.material.textview.MaterialTextView>(R.id.courseTitle)
        val backArrowImg = findViewById<ImageView>(R.id.backArrowImg)
        courseTitle.text = courseCategory

        backArrowImg.setOnClickListener {
            finish()
        }
    }
}