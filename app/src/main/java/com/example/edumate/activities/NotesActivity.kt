package com.example.edumate.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edumate.R
import com.example.edumate.adapters.NotesParentAdapter
import com.example.edumate.models.NotesChildItem
import com.example.edumate.models.NotesParentItem
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
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.notesToolbar)
        toolbar?.title = subject
        setSupportActionBar(toolbar)

        toolbar?.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setUpNotesList() {

        for (i in 1..5) {
            val notesChildrenList = mutableListOf<NotesChildItem>()

            var coll: String = when (i) {
                1 -> {
                    "Books"
                }
                2 -> {
                    "Lab Files"
                }
                3 -> {
                    "Important Questions"
                }
                4 -> {
                    "Handwritten Notes"
                }
                5 -> {
                    "Teacher's Notes"
                }
                else -> {
                    "More Stuffs"
                }
            }

            if(subject == "MST & PTU Final") {
                coll = "Exam"
            }

            if (department != null && semester != null && subject != null) {
                val collectionReference = firestore.collection(department!!)
                collectionReference.document(semester!!).collection("Subjects")
                    .document(subject!!)
                    .collection(coll)
                    .addSnapshotListener { value, error ->
                        if (value == null || error != null) {
                            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT)
                                .show()
                            return@addSnapshotListener
                        }
                        notesChildrenList.clear()
                        notesChildrenList.addAll(value.toObjects(NotesChildItem::class.java))
                        if (notesChildrenList.isNotEmpty()) {
                            when (i) {
                                1 -> {
                                    if(notesParentList.contains(
                                            NotesParentItem(
                                                coll,
                                                R.drawable.book_notes,
                                                notesChildrenList
                                            )
                                        )) {
                                        notesParentList.remove(
                                            NotesParentItem(
                                                coll,
                                                R.drawable.book_notes,
                                                notesChildrenList
                                            )
                                        )
                                    }
                                    notesParentList.add(
                                        NotesParentItem(
                                            coll,
                                            R.drawable.book_notes,
                                            notesChildrenList
                                        )
                                    )
                                }
                                2 -> {
                                    if(notesParentList.contains(
                                            NotesParentItem(
                                                coll,
                                                R.drawable.lab_file,
                                                notesChildrenList
                                            )
                                        )) {
                                        notesParentList.remove(
                                            NotesParentItem(
                                                coll,
                                                R.drawable.lab_file,
                                                notesChildrenList
                                            )
                                        )
                                    }
                                    notesParentList.add(
                                        NotesParentItem(
                                            coll,
                                            R.drawable.lab_file,
                                            notesChildrenList
                                        )
                                    )
                                }
                                3 -> {
                                    if(notesParentList.contains(
                                            NotesParentItem(
                                                coll,
                                                R.drawable.important_questions,
                                                notesChildrenList
                                            )
                                        )) {
                                        notesParentList.remove(
                                            NotesParentItem(
                                                coll,
                                                R.drawable.important_questions,
                                                notesChildrenList
                                            )
                                        )
                                    }
                                    notesParentList.add(
                                        NotesParentItem(
                                            coll,
                                            R.drawable.important_questions,
                                            notesChildrenList
                                        )
                                    )
                                }
                                4 -> {
                                    if(notesParentList.contains(
                                            NotesParentItem(
                                                coll,
                                                R.drawable.handwritting_notes,
                                                notesChildrenList
                                            )
                                        )) {
                                        notesParentList.remove(
                                            NotesParentItem(
                                                coll,
                                                R.drawable.handwritting_notes,
                                                notesChildrenList
                                            )
                                        )
                                    }
                                    notesParentList.add(
                                        NotesParentItem(
                                            coll,
                                            R.drawable.handwritting_notes,
                                            notesChildrenList
                                        )
                                    )
                                }
                                5 -> {
                                    if(notesParentList.contains(
                                            NotesParentItem(
                                                coll,
                                                R.drawable.teacher_notes,
                                                notesChildrenList
                                            )
                                        )) {
                                        notesParentList.remove(
                                            NotesParentItem(
                                                coll,
                                                R.drawable.teacher_notes,
                                                notesChildrenList
                                            )
                                        )
                                    }
                                    notesParentList.add(
                                        NotesParentItem(
                                            coll,
                                            R.drawable.teacher_notes,
                                            notesChildrenList
                                        )
                                    )
                                }
                                else -> {
                                    if(notesParentList.contains(
                                            NotesParentItem(
                                                coll,
                                                R.drawable.book_notes,
                                                notesChildrenList
                                            )
                                        )) {
                                        notesParentList.remove(
                                            NotesParentItem(
                                                coll,
                                                R.drawable.book_notes,
                                                notesChildrenList
                                            )
                                        )
                                    }
                                    notesParentList.add(
                                        NotesParentItem(
                                            coll,
                                            R.drawable.book_notes,
                                            notesChildrenList
                                        )
                                    )
                                }
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
            }
            if(subject == "MST & PTU Final") {
                break
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