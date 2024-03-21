package com.example.edumate.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.edumate.R
import com.example.edumate.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dialog: Dialog
    private lateinit var user: Users
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid ?: ""  // Retrieve the current user's ID or set an empty string if not available
        databaseReference = FirebaseDatabase.getInstance().getReference("users") // Get reference to the Firebase Realtime Database node for users
        setUpViews() // Call the method to set up views
    }

    private fun setUpViews() {
        setUpToolbar() // Set up the toolbar

        if (uid.isNotEmpty()) { // Check if the user ID is not empty
            getUserData() // Call method to fetch user data
        }

        logOut() // Set click listener for logout button
    }

    private fun logOut() {
        val logOut =
            findViewById<ImageView>(R.id.profileLogoutImg) // Find the logout ImageView in the layout

        logOut.setOnClickListener { // Set click listener for logout button
            auth.signOut() // Sign out the user
            val intent = Intent(this, LoginActivity::class.java) // Create intent to navigate to the LoginActivity
            startActivity(intent) // Start the LoginActivity
            Toast.makeText(this, "Log Out Successfully", Toast.LENGTH_SHORT).show() // showing toast message
            finish() // Finish the current activity
        }
    }

    private fun getUserData() {
        val name =
            findViewById<com.google.android.material.textview.MaterialTextView>(R.id.profileName) // Find the TextView for displaying user's name
        val email =
            findViewById<com.google.android.material.textview.MaterialTextView>(R.id.profileEmail) // Find the TextView for displaying user's email
        val collegeName =
            findViewById<com.google.android.material.textview.MaterialTextView>(R.id.profileCollegeName) // Find the TextView for displaying user's college name

        showProgressBar() // Show progress dialog

        // Add a listener for changes in the user's data in the database
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) { // Check if the user data exists in the snapshot
                    user = snapshot.getValue(Users::class.java)!! // Get user object from the snapshot
                    name.text = user.fullName ?: "" // Set user's name in the TextView
                    email.text = user.email ?: "" // Set user's email in the TextView
                    collegeName.text = user.collegeName ?: "" // Set user's college name in the TextView
                }
                hideProgressBar() // Hide progress dialog after data retrieval
            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressBar() // Hide progress dialog if data retrieval is cancelled
            }
        })
    }

    private fun setUpToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.profileToolbar) // Find the toolbar in the layout
        toolbar?.title = "Profile" // Set title of the toolbar
        setSupportActionBar(toolbar) // Set toolbar as support action bar

        toolbar?.setNavigationOnClickListener {
            finish() // Finish the activity when the back button on the toolbar is clicked
        }
    }

    private fun showProgressBar() {
        dialog = Dialog(this@ProfileActivity) // Create a new dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // Request no title for the dialog window
        dialog.setContentView(R.layout.progress_bar_layout) // Set layout for the progress dialog
        dialog.setCanceledOnTouchOutside(false) // Set dialog to not dismiss on outside touch
        dialog.show() // Show the progress dialog
    }

    private fun hideProgressBar() {
        dialog.dismiss() // Dismiss the progress dialog
    }
}
