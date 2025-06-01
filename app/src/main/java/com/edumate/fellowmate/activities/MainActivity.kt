package com.edumate.fellowmate.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.edumate.fellowmate.R
import com.edumate.fellowmate.fragments.BlogsList
import com.edumate.fellowmate.fragments.Courses
import com.edumate.fellowmate.fragments.Home
import com.edumate.fellowmate.fragments.OurTeam
import com.edumate.fellowmate.fragments.UserProfile
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private var currentFragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        if (savedInstanceState == null) {
            replaceFragment(Home())
            bottomNav.selectedItemId = R.id.home
        } else {
            // Restore the current fragment based on fragment manager
            currentFragment =
                supportFragmentManager.findFragmentById(R.id.frameLayout) ?: Home()
        }

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    if (currentFragment !is Home) replaceFragment(Home())
                    true
                }

                R.id.course -> {
                    if (currentFragment !is Courses) replaceFragment(Courses())
                    true
                }

                R.id.blogs -> {
                    if (currentFragment !is BlogsList) replaceFragment(BlogsList())
                    true
                }

                R.id.team -> {
                    if (currentFragment !is OurTeam) replaceFragment(OurTeam())
                    true
                }

                R.id.profile -> {
                    if (currentFragment !is UserProfile) replaceFragment(UserProfile())
                    true
                }

                else -> false
            }
        }

        MobileAds.initialize(this@MainActivity) {}
    }

    override fun onBackPressed() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        if (currentFragment !is Home) {
            replaceFragment(Home()) // Navigate back to Home fragment
            bottomNav.selectedItemId = R.id.home // Update BottomNavigationView
        } else {
            confirmExit() // Confirm exit if already on Home
        }
    }

    private fun confirmExit() {
        // Inflate the custom layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom_alert, null)

        // Build the dialog
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Set up the views
        val positiveButton: Button = dialogView.findViewById(R.id.positiveButton)
        val negativeButton: Button = dialogView.findViewById(R.id.negativeButton)

        positiveButton.setOnClickListener {
            finishAffinity() // Close the app
            exitProcess(0)
        }

        negativeButton.setOnClickListener {
            alertDialog.dismiss() // Close the dialog
        }

        // Show the dialog
        alertDialog.show()

        // Optional: Adjust the dialog width to match the screen width
        alertDialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun replaceFragment(fragment: Fragment) {
        if (currentFragment?.javaClass != fragment.javaClass) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commitAllowingStateLoss() // Handle possible state loss
            currentFragment = fragment
        }
    }


}