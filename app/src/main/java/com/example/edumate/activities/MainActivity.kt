package com.example.edumate.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.edumate.R
import com.example.edumate.adapters.DepartmentAdapter
import com.example.edumate.adapters.StudyPlaylistAdapter
import com.example.edumate.models.Department
import com.example.edumate.models.StudyPlaylist
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var firestore: FirebaseFirestore
    private lateinit var departmentAdapter: DepartmentAdapter
    private var departmentList = mutableListOf<Department>()
    private lateinit var playlistAdapter: StudyPlaylistAdapter
    private var playlistArrayList = mutableListOf<StudyPlaylist>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firestore = FirebaseFirestore.getInstance()

        setUpViews()

        val imageSlider = findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()
        imageList.add(
            SlideModel(
                "https://th.bing.com/th/id/OIP.v7gEKUNUq-Jy-ARL41hoxAHaE7?rs=1&pid=ImgDetMain",
                ""
            )
        )
        imageList.add(
            SlideModel(
                "https://www.thebluediamondgallery.com/wooden-tile/images/study.jpg",
                ""
            )
        )
        imageList.add(
            SlideModel(
                "https://th.bing.com/th/id/OIP.f043btCYeiwl0ag7Ru90kwHaE3?w=1200&h=789&rs=1&pid=ImgDetMain",
                ""
            )
        )
        imageList.add(
            SlideModel(
                "https://2.bp.blogspot.com/-iYm4yrdE_Mc/U6-88DeA9wI/AAAAAAAACuA/2JZq8WSOJDA/s1600/study-tips-to-study-better.jpg",
                "Study Tips"
            )
        )

        imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun setUpViews() {
        setUpDepartment()
        setUpDrawerLayout()
        setUpDepartmentRecyclerview()
        setUpStudyPlaylist()
    }

    private fun setUpStudyPlaylist() {
        val listViewArrow: ImageView = findViewById(R.id.studyPlaylistArrow)

        listViewArrow.setOnClickListener {
            val intent = Intent(this, StudyPlaylistActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpDepartmentRecyclerview() {
        val departmentRecyclerView = findViewById<RecyclerView>(R.id.departmentRecyclerView)
        departmentAdapter = DepartmentAdapter(this, departmentList)
        departmentRecyclerView.layoutManager = GridLayoutManager(this, 3)
        departmentRecyclerView.adapter = departmentAdapter
    }

    private fun setUpDepartment() {

        val collectionReference = firestore.collection("Departments List")

        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            departmentList.clear()
            departmentList.addAll(value.toObjects(Department::class.java))
            departmentAdapter.notifyDataSetChanged()
        }
    }

    private fun setUpDrawerLayout() {
        val appBar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.appBar)
        val mainDrawer = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.mainDrawer)
        setSupportActionBar(appBar)

        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, mainDrawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}