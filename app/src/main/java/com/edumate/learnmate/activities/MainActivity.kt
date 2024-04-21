package com.edumate.learnmate.activities

import android.content.ClipData.Item
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.edumate.learnmate.R
import com.edumate.learnmate.fragments.BlogsList
import com.edumate.learnmate.fragments.Home
import com.edumate.learnmate.fragments.UserProfile
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var currentFragment: Fragment? = null
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        replaceFragment(Home())

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    if (currentFragment !is Home) {
                        replaceFragment(Home())
                    }
                    true
                }

                R.id.blogs -> {
                    if(currentFragment !is BlogsList) {
                        replaceFragment(BlogsList())
                    }
                    true
                }

                R.id.profile -> {
                    if (currentFragment !is UserProfile) {
                        replaceFragment(UserProfile())
                    }
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (currentFragment != fragment) {
            currentFragment = fragment
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayout, fragment)
            fragmentTransaction.commit()
        }
    }
}