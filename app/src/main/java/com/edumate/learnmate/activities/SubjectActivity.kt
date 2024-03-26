package com.edumate.learnmate.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edumate.learnmate.R
import com.edumate.learnmate.adapters.SubjectAdapter
import com.edumate.learnmate.models.Subject
import com.google.firebase.firestore.FirebaseFirestore

class SubjectActivity : AppCompatActivity() {

    private lateinit var adapter: SubjectAdapter
    private var subjectList = mutableListOf<Subject>()
    private lateinit var firestore: FirebaseFirestore
    private var semester: String? = null
    private var department: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)
        firestore = FirebaseFirestore.getInstance()
        semester = intent.getStringExtra("TITLE")
        department = intent.getStringExtra("DEPARTMENT")

        setUpViews()
    }

    private fun setUpViews() {
        setUpToolbar()
        setUpRecyclerView()
        setUpSubjectList()
    }

    private fun setUpToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.subjectToolbar)
        toolbar?.title = semester
        setSupportActionBar(toolbar)

        toolbar?.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setUpSubjectList() {
        if (department != null && semester != null) {
            val collectionReference = firestore.collection(department!!)

            collectionReference.document(semester!!).collection("Subjects")
                .addSnapshotListener { value, error ->
                    if (value == null || error != null) {
                        Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }

                    subjectList.clear()
                    subjectList.addAll(value.toObjects(Subject::class.java))
                    adapter.notifyDataSetChanged()
                }

        }
    }

    private fun setUpRecyclerView() {
        val semesterRecyclerView = findViewById<RecyclerView>(R.id.subjectRecyclerView)
        adapter = SubjectAdapter(this, subjectList)
        semesterRecyclerView.layoutManager = GridLayoutManager(this, 2)
        semesterRecyclerView.adapter = adapter
    }
}