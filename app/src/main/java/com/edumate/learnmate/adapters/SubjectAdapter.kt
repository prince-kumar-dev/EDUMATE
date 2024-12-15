package com.edumate.learnmate.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edumate.learnmate.R
import com.edumate.learnmate.activities.NotesActivity
import com.edumate.learnmate.models.Subject
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError

class SubjectAdapter(private var context: Context, private val subject: List<Subject>) :
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    private var mInterstitialAd: InterstitialAd? = null
    private val TAG = "SubjectAdapter"

    init {
        // Load the interstitial ad when the adapter is created
        loadInterstitialAd()
    }

    inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subjectViewTitle: TextView = itemView.findViewById(R.id.subjectTitle)
        val iconView: ImageView = itemView.findViewById(R.id.subjectIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.subject_item, parent, false)
        return SubjectViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subject.size
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.subjectViewTitle.text = subject[position].subjectName

        Glide.with(context)
            .load(subject[position].iconUrl)
            .into(holder.iconView)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, NotesActivity::class.java)
            intent.putExtra("TITLE", subject[position].subjectName)
            intent.putExtra("SEMESTER", subject[position].semester)
            intent.putExtra("DEPARTMENT", subject[position].department)

            if (mInterstitialAd != null) {
                // Set ad callbacks and show the ad
                mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad dismissed.")
                        mInterstitialAd = null
                        loadInterstitialAd() // Load the next ad
                        context.startActivity(intent) // Navigate to NotesActivity after ad
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.e(TAG, "Ad failed to show: ${adError.message}")
                        mInterstitialAd = null
                        context.startActivity(intent) // Navigate to NotesActivity directly
                    }
                }
                mInterstitialAd?.show(context as androidx.appcompat.app.AppCompatActivity)
            } else {
                Log.d(TAG, "Ad not ready. Navigating to Notes Page.")
                context.startActivity(intent)
            }
        }
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            "ca-app-pub-9437359488548120/3751414497", // Replace with your actual Ad Unit ID
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    Log.d(TAG, "Ad failed to load: ${p0.message}")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad loaded.")
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }

}