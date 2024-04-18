package com.edumate.learnmate.fragments

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.edumate.learnmate.R
import com.edumate.learnmate.activities.CodingPlaylistActivity
import com.edumate.learnmate.activities.ForgetPasswordActivity
import com.edumate.learnmate.activities.PlacementSeriesActivity
import com.edumate.learnmate.activities.StudyPlaylistActivity
import com.edumate.learnmate.adapters.DepartmentAdapter
import com.edumate.learnmate.models.Department
import com.edumate.learnmate.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore


class Home : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var departmentAdapter: DepartmentAdapter
    private var departmentList = mutableListOf<Department>()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var uid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid
            ?: ""  // Retrieve the current user's ID or set an empty string if not available
        databaseReference = FirebaseDatabase.getInstance()
            .getReference("users") // Get reference to the Firebase Realtime Database node for users

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        setUpViews(view) // Call method to set up view

        return view
    }

    private fun setUpViews(view: View) {
        if (uid.isNotEmpty()) { // Check if the user ID is not empty
            setUpToolbar(view) // Call method to fetch user data
        }
        setUpImageSlide(view)
        setUpDepartment()
        setUpDepartmentRecyclerview(view)
        setUpStudyPlaylist(view)
        setUpCodingPlaylist(view)
        setUpPlacementSeries(view)
        storeUserDetailsToLocalDevice()
    }

    private fun storeUserDetailsToLocalDevice() {
        val sharedPreferences = context?.getSharedPreferences("User_Details", Context.MODE_PRIVATE)

        if (sharedPreferences == null || !sharedPreferences.contains("user_name")) {
            // SharedPreferences is null or user details are not available, fetch from Firebase
            val uid = auth.currentUser?.uid ?: ""
            val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid)

            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(Users::class.java)

                        // Save user details into SharedPreferences
                        sharedPreferences?.edit()?.apply {
                            putString("user_name", user?.fullName)
                            putString("user_email", user?.email)
                            putString("user_college_name", user?.collegeName)
                            apply()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error, log or show a message to the user
                }
            })
        } else {
            // User details are already available in SharedPreferences, no need to fetch from Firebase
        }
    }

    private fun setUpToolbar(view: View) {
        val homeToolbar =
            view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeToolbar) // Find the TextView for displaying user's name

        homeToolbar.title = ""
        (activity as AppCompatActivity?)!!.setSupportActionBar(homeToolbar)

        val titleToolbarTxt =
            view.findViewById<com.google.android.material.textview.MaterialTextView>(R.id.titleToolbarTxt)
        val userName = getUserName()
        val index = userName?.indexOf(' ')

        if (index != -1 && userName != null) {
            titleToolbarTxt.text = "Hello " + index?.let { userName.substring(0, it) } + "!"
        } else if (userName != null) {
            titleToolbarTxt.text = "Hello " + userName + "!"
        }

        context?.let { ContextCompat.getColor(it, R.color.black1) }
            ?.let { homeToolbar.overflowIcon?.setColorFilter(it, PorterDuff.Mode.SRC_ATOP) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.contactUs -> {
                val intent = Intent(Intent.ACTION_VIEW)
                val data =
                    Uri.parse("mailto:edumate.contact@gmail.com?subject=Want to Resolve Query &body=")
                intent.data = data
                startActivity(intent)
                return true
            }

            R.id.share -> {
                val url = "https://play.google.com/store/apps/details?id=com.edumate.learnmate"
                val intent = Intent(Intent.ACTION_SEND)
                val sub = "EDUMATE: Your Learning Partner"
                val body = "Discover the magic of Edumate! Say goodbye to study stress with our easy-to-use platform. Dive into structured notes, engaging placement series, curated YouTube playlists, and thrilling coding sessions. Experience the joy of learning with Edumate's user-friendly interface!"

                intent.type = "text/html"
                intent.putExtra(Intent.EXTRA_SUBJECT, sub)
                intent.putExtra(Intent.EXTRA_TEXT, "$sub\n\n$body\n\n$url")
                startActivity(Intent.createChooser(intent, "Share Via"))
                return true
            }

            R.id.rateUs -> {
                val url =
                    "https://play.google.com/store/apps/details?id=com.edumate.learnmate"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                return true
            }

            R.id.forgetPassword -> {
                val intent = Intent(activity, ForgetPasswordActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getUserName(): String? {
        val sharedPreferences =
            context?.getSharedPreferences("User_Details", Context.MODE_PRIVATE)
        return sharedPreferences?.getString("user_name", null)
    }

    private fun setUpPlacementSeries(view: View) {
        // Set up onClickListener for navigating to PlacementSeriesActivity
        val placementSeries = view.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.placementSeries)

        placementSeries?.setOnClickListener {
            val intent = Intent(activity, PlacementSeriesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpCodingPlaylist(view: View) {
        val codingPlaylist = view.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.codingPlaylist)

        codingPlaylist?.setOnClickListener {
            val intent = Intent(activity, CodingPlaylistActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpStudyPlaylist(view: View) {
        val studyPlaylist = view.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.studyPlaylist)

        studyPlaylist?.setOnClickListener {
            val intent = Intent(activity, StudyPlaylistActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpDepartmentRecyclerview(view: View) {
        // Set up RecyclerView for displaying departments
        val departmentRecyclerView =
            view.findViewById<RecyclerView>(R.id.departmentRecyclerView)
        departmentAdapter = context?.let { DepartmentAdapter(it, departmentList) }!!
        departmentRecyclerView?.layoutManager = GridLayoutManager(context, 3)
        departmentRecyclerView?.adapter = departmentAdapter
    }

    private fun setUpDepartment() {
        // Fetch departments data from Firestore and populate the list
        val collectionReference = firestore.collection("Departments List")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            departmentList.clear()
            departmentList.addAll(value.toObjects(Department::class.java))
            departmentAdapter.notifyDataSetChanged()
        }
    }

    private fun setUpImageSlide(view: View) {
        val imageSlider = view.findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.welcome_to_edumate))
        // Access Firestore collection
        val collectionReference = firestore.collection("ImageSlide")
        collectionReference.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Retrieve data from each document
                    val url = document.getString("url")
                    val title = document.getString("title")
                    // Add data to imageList
                    if (url != null) {
                        if (title.isNullOrEmpty()) {
                            imageList.add(SlideModel(url))
                        } else {
                            imageList.add(SlideModel(url, title))
                        }
                    }
                }
                // Set up the image slider with the prepared image list
                imageSlider?.setImageList(imageList, ScaleTypes.FIT)
            }
            .addOnFailureListener { exception ->
                // Handle errors
                Log.e("Firestore", "Error fetching data:", exception)
                Toast.makeText(
                    context,
                    "Error fetching data: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}