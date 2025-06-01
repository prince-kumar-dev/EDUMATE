package com.edumate.fellowmate.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edumate.fellowmate.R
import com.edumate.fellowmate.adapters.NotesParentAdapter
import com.edumate.fellowmate.models.NotesChildItem
import com.edumate.fellowmate.models.NotesParentItem
import com.google.firebase.firestore.FirebaseFirestore

class NotesActivity : AppCompatActivity() {

    private val notesParentList = mutableListOf<NotesParentItem>()
    private lateinit var adapter: NotesParentAdapter
    private lateinit var firestore: FirebaseFirestore
    private var semester: String? = null
    private var department: String? = null
    private var subject: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        firestore = FirebaseFirestore.getInstance()
        subject = intent.getStringExtra("TITLE")
        semester = intent.getStringExtra("SEMESTER")
        department = intent.getStringExtra("DEPARTMENT")

        setUpViews()
    }

    private fun setUpViews() {
        setUpNotesList()
        setUpToolbar()
        setUpRecyclerView()
    }

    private fun setUpToolbar() {
        val notesTitle = findViewById<com.google.android.material.textview.MaterialTextView>(R.id.notesTitle)
        val backArrowImg = findViewById<ImageView>(R.id.backArrowImg)

        notesTitle.text = subject

        backArrowImg.setOnClickListener {
            finish()
        }
    }


    private fun setUpNotesList() {
        val collections = mapOf(
            "Important Questions" to R.drawable.important_questions,
            "Exam" to R.drawable.exam_notes,
            "Lab Files" to R.drawable.lab_file,
            "Books" to R.drawable.book_notes,
            "Handwritten Notes" to R.drawable.handwritting_notes,
            "Teacher's Notes" to R.drawable.teacher_notes,
            "Module Notes" to R.drawable.module,
            "Unit Notes" to R.drawable.module,
            "PPT Notes" to R.drawable.ppt,
            "More Stuffs" to R.drawable.book_notes // Default drawable resource
        )

        val coll = "Subjects"

        if (department != null && semester != null && subject != null) {
            val collectionReference = firestore.collection(department!!)
                .document(semester!!)
                .collection(coll)
                .document(subject!!)

            collections.forEach { (collectionName, drawableRes) ->
                collectionReference.collection(collectionName)
                    .addSnapshotListener { value, error ->
                        if (value == null || error != null) {
                            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                            return@addSnapshotListener
                        }

                        val notesChildrenList = value.toObjects(NotesChildItem::class.java)
                        if (notesChildrenList.isNotEmpty()) {
                            val existingItem = notesParentList.find { it.title == collectionName }
                            if (existingItem != null) {
                                notesParentList.remove(existingItem)
                            }
                            notesParentList.add(
                                NotesParentItem(
                                    collectionName,
                                    drawableRes,
                                    notesChildrenList
                                )
                            )
                        }
                        adapter.notifyDataSetChanged()
                    }
            }
        }
    }


    private fun setUpRecyclerView() {
        val notesRecyclerView = findViewById<RecyclerView>(R.id.notesParentRecyclerView)
        adapter = NotesParentAdapter(this, notesParentList)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesRecyclerView.adapter = adapter
    }
}