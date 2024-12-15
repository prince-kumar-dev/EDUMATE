package com.edumate.learnmate.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edumate.learnmate.R
import com.edumate.learnmate.adapters.SubjectAdapter
import com.edumate.learnmate.models.Subject
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.firestore.FirebaseFirestore

class SubjectActivity : AppCompatActivity() {

    private lateinit var adapter: SubjectAdapter
    private var subjectList = mutableListOf<Subject>()
    private lateinit var firestore: FirebaseFirestore
    private var semester: String? = null
    private var department: String? = null
    private lateinit var adView1: AdView
    private lateinit var adView2: AdView

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
        setUpAds()
    }


    private fun setUpAds() {
        // Get reference to the ad container
        val adContainer1 = findViewById<FrameLayout>(R.id.adViewContainer1)
        val adContainer2 = findViewById<FrameLayout>(R.id.adViewContainer2)
        // Create and configure the AdView
        adView1 = AdView(this).apply {
            adUnitId = "ca-app-pub-9437359488548120/8710530464" // Replace with your AdMob Ad Unit ID
        }

        adView2 = AdView(this).apply {
            adUnitId = "ca-app-pub-9437359488548120/3578323986" // Replace with your AdMob Ad Unit ID
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
        val subjectTitle = findViewById<com.google.android.material.textview.MaterialTextView>(R.id.subjectTitle)
        val backArrowImg = findViewById<ImageView>(R.id.backArrowImg)
        subjectTitle.text = semester

        backArrowImg.setOnClickListener {
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