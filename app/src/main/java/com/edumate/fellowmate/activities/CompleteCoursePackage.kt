package com.edumate.fellowmate.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edumate.fellowmate.R
import com.edumate.fellowmate.adapters.CompleteCoursePackageParentAdapter
import com.edumate.fellowmate.models.CompleteChildCoursePackage
import com.edumate.fellowmate.models.CompleteParentCoursePackage
import com.google.firebase.firestore.FirebaseFirestore

class CompleteCoursePackage : AppCompatActivity() {

    private var coursePackageParentList = mutableListOf<CompleteParentCoursePackage>()
    private lateinit var adapter: CompleteCoursePackageParentAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_course_package)
        firestore = FirebaseFirestore.getInstance()

        val coursePackageName = intent.getStringExtra("TITLE")
        if (coursePackageName != null)
            setUpViews(coursePackageName)
    }

    private fun setUpViews(coursePackageName: String) {
        setUpToolbar(coursePackageName)
        setUpRecyclerView()
        setUpCoursePackageList(coursePackageName)
    }

    private fun setUpRecyclerView() {
        val completeCourseRecyclerView = findViewById<RecyclerView>(R.id.completeCourseRecyclerView)

        adapter = CompleteCoursePackageParentAdapter(this, coursePackageParentList)
        completeCourseRecyclerView.layoutManager = LinearLayoutManager(this)
        completeCourseRecyclerView.adapter = adapter
    }

    private fun setUpCoursePackageList(coursePackageName: String) {
        val collectionReference = firestore.collection(coursePackageName)

        collectionReference.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {

                    val id = document.id
                    val title = document.getString("stepTitle")
                    val description = document.getString("stepDescription")

                    val childItemList = mutableListOf<CompleteChildCoursePackage>()

                    collectionReference.document(id).collection("course")
                        .get()
                        .addOnSuccessListener { childQuerySnapshot ->
                            childItemList.clear()
                            childItemList.addAll(
                                childQuerySnapshot.toObjects(
                                    CompleteChildCoursePackage::class.java
                                )
                            )

                            val existingParentItemIndex =
                                coursePackageParentList.indexOfFirst {
                                    it.stepTitle == title && it.stepDescription == description
                                }

                            if (existingParentItemIndex != -1) {
                                coursePackageParentList[existingParentItemIndex] =
                                    CompleteParentCoursePackage(
                                        title!!,
                                        description!!,
                                        childItemList
                                    )
                            } else {
                                coursePackageParentList.add(
                                    CompleteParentCoursePackage(
                                        title!!,
                                        description!!,
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

    private fun setUpToolbar(coursePackageName: String) {
        val semesterTitle =
            findViewById<com.google.android.material.textview.MaterialTextView>(R.id.completeCoursePackageTitle)
        val backArrowImg = findViewById<ImageView>(R.id.backArrowImg)
        semesterTitle.text = coursePackageName

        backArrowImg.setOnClickListener {
            finish()
        }
    }
}