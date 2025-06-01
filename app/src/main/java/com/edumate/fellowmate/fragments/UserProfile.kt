package com.edumate.fellowmate.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.edumate.fellowmate.activities.LoginActivity
import com.edumate.fellowmate.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserProfile : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var uid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid
            ?: ""  // Retrieve the current user's ID or set an empty string if not available
        databaseReference = FirebaseDatabase.getInstance()
            .getReference("users") // Get reference to the Firebase Realtime Database node for users

        setUpViews(view)

        return view
    }

    private fun setUpViews(view: View) {

        if (uid.isNotEmpty()) { // Check if the user ID is not empty
            getUserData(view) // Call method to fetch user data
        }

        logOut(view) // Set click listener for logout button
        shareApp(view)
    }

    private fun shareApp(view: View) {
        val shareBtn = view.findViewById<Button>(R.id.shareButton)

        shareBtn.setOnClickListener {
            val url = "https://play.google.com/store/apps/details?id=com.edumate.learnmate"
            val intent = Intent(Intent.ACTION_SEND)
            val sub = "EDUMATE: Your Learning Partner"
            val body = "Discover the magic of Edumate! Say goodbye to study stress with our easy-to-use platform. Dive into structured notes, engaging placement series, curated YouTube playlists, and thrilling coding sessions. Experience the joy of learning with Edumate's user-friendly interface!\n\nDownload our app via link given below:"

            intent.type = "text/html"
            intent.putExtra(Intent.EXTRA_SUBJECT, sub)
            intent.putExtra(Intent.EXTRA_TEXT, "$sub\n\n$body\n$url")
            startActivity(Intent.createChooser(intent, "Share Via"))
        }
    }

    private fun getUserData(view: View) {
        val name =
            view.findViewById<com.google.android.material.textview.MaterialTextView>(R.id.profileName) // Find the TextView for displaying user's name
        val email =
            view.findViewById<com.google.android.material.textview.MaterialTextView>(R.id.profileEmail) // Find the TextView for displaying user's email
        val collegeName =
            view.findViewById<com.google.android.material.textview.MaterialTextView>(R.id.profileCollegeName) // Find the TextView for displaying user's college name

        name.text = getUserName()
        email.text = getUserEmail()
        collegeName.text = getUserCollegeName()
    }

    private fun getUserName(): String? {
        val sharedPreferences = context?.getSharedPreferences("User_Details", Context.MODE_PRIVATE)
        return sharedPreferences?.getString("user_name", null)
    }

    private fun getUserEmail(): String? {
        val sharedPreferences = context?.getSharedPreferences("User_Details", Context.MODE_PRIVATE)
        return sharedPreferences?.getString("user_email", null)
    }

    private fun getUserCollegeName(): String? {
        val sharedPreferences = context?.getSharedPreferences("User_Details", Context.MODE_PRIVATE)
        return sharedPreferences?.getString("user_college_name", null)
    }

    private fun logOut(view: View) {
        val logOut =
            view.findViewById<Button>(R.id.logoutButton) // Find the logout ImageView in the layout

        logOut.setOnClickListener { // Set click listener for logout button
            auth.signOut() // Sign out the user
            val intent = Intent(
                activity,
                LoginActivity::class.java
            ) // Create intent to navigate to the LoginActivity
            startActivity(intent) // Start the LoginActivity
            activity?.finish()
            Toast.makeText(activity, "Log Out Successfully", Toast.LENGTH_SHORT)
                .show() // showing toast message
        }
    }
}