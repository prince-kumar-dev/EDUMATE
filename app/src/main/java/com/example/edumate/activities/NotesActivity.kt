package com.example.edumate.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edumate.R
import com.example.edumate.adapters.NotesParentAdapter
import com.example.edumate.models.NotesChildItem
import com.example.edumate.models.NotesParentItem

class NotesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val notesParentList = ArrayList<NotesParentItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notes)

        recyclerView = findViewById(R.id.notesParentRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addDataToList()
        val adapter = NotesParentAdapter(notesParentList)
        recyclerView.adapter = adapter
    }

    private fun addDataToList() {
        val notesChildItem1 = ArrayList<NotesChildItem>()
        notesChildItem1.add(NotesChildItem("C", R.drawable.book))
        notesChildItem1.add(NotesChildItem("C++", R.drawable.bca_branch))
        notesChildItem1.add(NotesChildItem("Java", R.drawable.article))
        notesChildItem1.add(NotesChildItem("Python", R.drawable.cse_branch))

        notesParentList.add(NotesParentItem("Game Development", R.drawable.ece_branch, notesChildItem1))

        notesParentList.add(NotesParentItem("Data Science", R.drawable.forget_password, notesChildItem1))

        notesParentList.add(NotesParentItem("Programming", R.drawable.it_branch, notesChildItem1))

        notesParentList.add(NotesParentItem("Programming", R.drawable.it, notesChildItem1))

        notesParentList.add(NotesParentItem("Programming", R.drawable.home, notesChildItem1))
    }
}