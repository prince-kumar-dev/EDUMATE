package com.example.edumate.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.edumate.R
import com.example.edumate.adapters.DepartmentAdapter
import com.example.edumate.models.Department
import com.example.edumate.models.ImageSlide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var departmentAdapter: DepartmentAdapter
    private var departmentList = mutableListOf<Department>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        setUpViews() // Call method to set up views

    }

    private fun setUpViews() {
        // Call methods to set up various components of the activity
        setUpImageSlide()
        setUpDepartment()
        setUpDrawerLayout()
        setUpDepartmentRecyclerview()
        setUpStudyPlaylist()
        setUpCodingPlaylist()
        setUpPlacementSeries()
    }

    private fun setUpImageSlide() {
        val imageSlider = findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.welcome_to_edumate))
        val collectionReference = firestore.collection("ImageSlide")
        collectionReference.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Retrieve data from each document
                    val url = document.getString("url")
                    val title = document.getString("title")
                    // Add data to imageList
                    if (url != null && title != null) {
                        imageList.add(SlideModel(url, title))
                    }
                }
                // Set up the image slider with the prepared image list
                imageSlider.setImageList(imageList, ScaleTypes.FIT)
            }
            .addOnFailureListener { exception ->
                // Handle errors
                Toast.makeText(
                    this,
                    "Error fetching data: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


    private fun setUpPlacementSeries() {
        // Set up onClickListener for navigating to PlacementSeriesActivity
        val listViewArrow: ImageView = findViewById(R.id.placementArrow)

        listViewArrow.setOnClickListener {
            val intent = Intent(this, PlacementSeriesActivity::class.java)
            startActivity(intent)
        }
    }

    // Add similar methods for setting up other functionalities
    private fun setUpCodingPlaylist() {
        val listViewArrow: ImageView = findViewById(R.id.codingPlaylistArrow)

        listViewArrow.setOnClickListener {
            val intent = Intent(this, CodingPlaylistActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpStudyPlaylist() {
        val listViewArrow: ImageView = findViewById(R.id.studyPlaylistArrow)

        listViewArrow.setOnClickListener {
            val intent = Intent(this, StudyPlaylistActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpDepartmentRecyclerview() {
        // Set up RecyclerView for displaying departments
        val departmentRecyclerView = findViewById<RecyclerView>(R.id.departmentRecyclerView)
        departmentAdapter = DepartmentAdapter(this, departmentList)
        departmentRecyclerView.layoutManager = GridLayoutManager(this, 3)
        departmentRecyclerView.adapter = departmentAdapter
    }

    private fun setUpDepartment() {
        // Fetch departments data from Firestore and populate the list
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
        // Set up navigation drawer
        val appBar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.appBar)
        val mainDrawer = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.mainDrawer)
        val navigationView =
            findViewById<com.google.android.material.navigation.NavigationView>(R.id.navigationView)
        setSupportActionBar(appBar)

        // Set up navigation drawer
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, mainDrawer, R.string.open, R.string.close)
        actionBarDrawerToggle.syncState()

        // Set up item click listener for navigation menu items
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                // Handle different menu items
                R.id.placement -> {
                    val intent = Intent(this, PlacementSeriesActivity::class.java)
                    startActivity(intent)
                    mainDrawer.closeDrawers()
                    true
                }

                R.id.articles -> {
                    Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.r_and_d -> {
                    Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.studyPlaylist -> {
                    val intent = Intent(this, StudyPlaylistActivity::class.java)
                    startActivity(intent)
                    mainDrawer.closeDrawers()
                    true
                }

                R.id.codingPlaylist -> {
                    val intent = Intent(this, CodingPlaylistActivity::class.java)
                    startActivity(intent)
                    mainDrawer.closeDrawers()
                    true
                }

                R.id.cse -> {
                    val intent = Intent(this, SemesterActivity::class.java)
                    intent.putExtra("TITLE", "CSE")
                    startActivity(intent)
                    mainDrawer.closeDrawers()
                    true
                }

                R.id.it -> {
                    val intent = Intent(this, SemesterActivity::class.java)
                    intent.putExtra("TITLE", "IT")
                    startActivity(intent)
                    mainDrawer.closeDrawers()
                    true
                }

                R.id.ece -> {
                    val intent = Intent(this, SemesterActivity::class.java)
                    intent.putExtra("TITLE", "ECE")
                    startActivity(intent)
                    mainDrawer.closeDrawers()
                    true
                }

                R.id.me -> {
                    val intent = Intent(this, SemesterActivity::class.java)
                    intent.putExtra("TITLE", "ME")
                    startActivity(intent)
                    mainDrawer.closeDrawers()
                    true
                }

                R.id.bca -> {
                    val intent = Intent(this, SemesterActivity::class.java)
                    intent.putExtra("TITLE", "BCA")
                    startActivity(intent)
                    mainDrawer.closeDrawers()
                    true
                }

                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    mainDrawer.closeDrawers()
                    true
                }

                R.id.forgetPassword -> {
                    val intent = Intent(this, ForgetPasswordActivity::class.java)
                    startActivity(intent)
                    mainDrawer.closeDrawers()
                    true
                }

                R.id.logOut -> {
                    auth.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Log Out Successfully", Toast.LENGTH_SHORT).show()
                    finish()
                    true
                }

                R.id.share -> {
//                    val shareIntent = Intent(Intent.ACTION_SEND)
//                    shareIntent.type = "text/plain"
//                    val shareBody =
//                        "Hey there, meet your new study buddy – Edumate! It's like having a super-smart friend who helps you ace your studies. Let's make learning awesome together!" // Replace with your desired text
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
//                    startActivity(Intent.createChooser(shareIntent, "Share via"))
                    Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.rateUs -> {
                    Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.contactUs -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    val data =
                        Uri.parse("mailto:edumate.contact@gmail.com?subject=Want to Resolve Query &body=")
                    intent.data = data
                    startActivity(intent)

                    true
                }
                // Add more cases for other menu items
                else -> {
                    false
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle ActionBarDrawerToggle clicks
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}