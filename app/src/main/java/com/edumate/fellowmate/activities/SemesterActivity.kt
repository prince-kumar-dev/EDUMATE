package com.edumate.fellowmate.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edumate.fellowmate.R
import com.edumate.fellowmate.adapters.SemesterAdapter
import com.edumate.fellowmate.models.Semester
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.firebase.firestore.FirebaseFirestore

class SemesterActivity : AppCompatActivity() {

    private lateinit var adapter: SemesterAdapter
    private var semesterList = mutableListOf<Semester>()
    private lateinit var firestore: FirebaseFirestore
    private var department: String? = null
    private lateinit var adView1: AdView
    private lateinit var adView2: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semester)
        firestore = FirebaseFirestore.getInstance()
        department = intent.getStringExtra("TITLE")

        setUpViews()
    }

    private fun setUpViews() {
        setUpToolbar()
        setUpRecyclerView()
        setUpSemesterList()
        setUpAds()
    }

    private fun setUpAds() {
        // Get reference to the ad container
        val adContainer1 = findViewById<FrameLayout>(R.id.adViewContainer1)
        val adContainer2 = findViewById<FrameLayout>(R.id.adViewContainer2)
        // Create and configure the AdView
        adView1 = AdView(this).apply {
            adUnitId = "ca-app-pub-9437359488548120/8596547066" // Replace with your AdMob Ad Unit ID
        }

        adView2 = AdView(this).apply {
            adUnitId = "ca-app-pub-9437359488548120/7746499821" // Replace with your AdMob Ad Unit ID
        }

        // Calculate and set the ad size
        adSize.let { size ->
            adView1.setAdSize(size)
            // Add AdView to the container
            adContainer1.addView(adView1)
            // Load an ad
            val adRequest = AdRequest.Builder().build()
            adView1.loadAd(adRequest)
        }

        // Calculate and set the ad size
        adSize.let { size ->
            adView2.setAdSize(size)
            // Add AdView to the container
            adContainer2.addView(adView2)
            // Load an ad
            val adRequest = AdRequest.Builder().build()
            adView2.loadAd(adRequest)
        }
    }

    // Get the ad size based on the screen width
    private val adSize: AdSize
        get() {
            val displayMetrics = resources.displayMetrics
            val adWidthPixels = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this.windowManager.currentWindowMetrics.bounds.width()
            } else {
                displayMetrics.widthPixels
            }
            val density = displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    override fun onDestroy() {
        adView1.destroy()
        adView2.destroy()
        super.onDestroy()
    }

    private fun setUpToolbar() {
        val semesterTitle = findViewById<com.google.android.material.textview.MaterialTextView>(R.id.semesterTitle)
        val backArrowImg = findViewById<ImageView>(R.id.backArrowImg)
        semesterTitle.text = department

        backArrowImg.setOnClickListener {
            finish()
        }
    }

    private fun setUpSemesterList() {
        val department = intent.getStringExtra("TITLE")
        if (department != null) {
            val collectionReference = firestore.collection(department)

            collectionReference.addSnapshotListener { value, error ->
                if (value == null || error != null) {
                    Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }
                semesterList.clear()
                semesterList.addAll(value.toObjects(Semester::class.java))
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setUpRecyclerView() {
        val semesterRecyclerView = findViewById<RecyclerView>(R.id.semesterRecyclerView)
        adapter = SemesterAdapter(this, semesterList)
        semesterRecyclerView.layoutManager = LinearLayoutManager(this)
        semesterRecyclerView.adapter = adapter
    }
}