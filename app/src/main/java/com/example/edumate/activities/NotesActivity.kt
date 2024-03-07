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

    private lateinit var recyclerView: RecyclerView
    private val notesParentList = ArrayList<NotesParentItem>()
    private val notesChildrenList = ArrayList<NotesChildItem>()
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
        setUpRecyclerView()
        setUpNotesList()
    }

    private fun setUpNotesList() {
        if (department != null && semester != null && subject != null) {
            val collectionReference = firestore.collection(department!!)
            collectionReference.document(semester!!).collection("Subjects").document(subject!!)
                .collection("Books")
                .addSnapshotListener { value, error ->
                    if (value == null || error != null) {
                        Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }
                    notesChildrenList.clear()
                    notesChildrenList.addAll(value.toObjects(NotesChildItem::class.java))
                    adapter.notifyDataSetChanged()
                    notesParentList.add(
                        NotesParentItem(
                            "Books",
                            R.drawable.book,
                            notesChildrenList
                        )
                    )
                }
        }

        if (department != null && semester != null && subject != null) {
            val collectionReference = firestore.collection(department!!)
            collectionReference.document(semester!!).collection("Subjects").document(subject!!)
                .collection("Handwritten Notes")
                .addSnapshotListener { value, error ->
                    if (value == null || error != null) {
                        Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }
                    notesChildrenList.clear()
                    notesChildrenList.addAll(value.toObjects(NotesChildItem::class.java))
                    adapter.notifyDataSetChanged()
                }
            notesParentList.add(
                NotesParentItem(
                    "Handwritten Notes",
                    R.drawable.bca_branch,
                    notesChildrenList
                )
            )
        }
    }

    private fun setUpRecyclerView() {
        val notesRecyclerView = findViewById<RecyclerView>(R.id.notesParentRecyclerView)
        adapter = NotesParentAdapter(this, notesParentList)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesRecyclerView.adapter = adapter
    }
}