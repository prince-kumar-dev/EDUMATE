package com.edumate.learnmate.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.edumate.learnmate.R
import com.google.firebase.storage.FirebaseStorage

class OurTeam : Fragment() {

    private var isFragmentDestroyed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isFragmentDestroyed = false // Reset the flag
        val view = inflater.inflate(R.layout.fragment_our_team, container, false)
        setUpViews(view)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFragmentDestroyed = true // Set the flag to indicate the fragment view is destroyed
    }

    private fun setUpViews(view: View) {
        setFounderProfile(view)
        setDeveloperProfile(view)
    }

    private fun setDeveloperProfile(view: View) {
        val img2 = view.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.img2)
        val storageReference = FirebaseStorage.getInstance().getReference("OurTeam/developer.jpg")

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            if (isFragmentDestroyed) return@addOnSuccessListener // Prevent operations on a destroyed fragment
            Glide.with(requireContext())
                .load(uri)
                .placeholder(R.drawable.developer)
                .error(R.drawable.error)
                .into(img2)
        }.addOnFailureListener { exception ->
            if (!isFragmentDestroyed) exception.printStackTrace()
        }

        val linkedinBtn2 = view.findViewById<ImageView>(R.id.linkedinBtn2)
        // Setting the Linkedin button

        linkedinBtn2.setOnClickListener {
            val url = "https://www.linkedin.com/in/prince-kumar-web/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addCategory(Intent.CATEGORY_BROWSABLE) // Ensures browser compatibility

            // Check if there's an app to handle the intent
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                // Explicitly open the default browser if no app is found
                try {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "Unable to open the link. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setFounderProfile(view: View) {
        val img1 = view.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.img1)
        val storageReference = FirebaseStorage.getInstance().getReference("OurTeam/founder.jpg")

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            if (isFragmentDestroyed) return@addOnSuccessListener // Prevent operations on a destroyed fragment
            Glide.with(requireContext())
                .load(uri)
                .placeholder(R.drawable.founder)
                .error(R.drawable.error)
                .into(img1)
        }.addOnFailureListener { exception ->
            if (!isFragmentDestroyed) exception.printStackTrace()
        }

        // Setting the Linkedin button
        val linkedinBtn1 = view.findViewById<ImageView>(R.id.linkedinBtn1)
        linkedinBtn1.setOnClickListener {
            val url = "https://www.linkedin.com/in/harsh-jindal-web/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addCategory(Intent.CATEGORY_BROWSABLE) // Ensures browser compatibility

            // Check if there's an app to handle the intent
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                // Explicitly open the default browser if no app is found
                try {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "Unable to open the link. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Setting the Mail button
        val mail1 = view.findViewById<ImageView>(R.id.mail1)
        mail1.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW)
            val data =
                Uri.parse("mailto:jindalharsh961@gmail.com?subject=Query&body=")
            intent.data = data
            startActivity(intent)
        }

        // Setting the Instagram button
        val instagram1 = view.findViewById<ImageView>(R.id.instagram1)
        instagram1.setOnClickListener {
            val url = "https://www.instagram.com/harsh_._jindal/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addCategory(Intent.CATEGORY_BROWSABLE) // Ensures browser compatibility

            // Check if there's an app to handle the intent
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                // Explicitly open the default browser if no app is found
                try {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "Unable to open Instagram. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}