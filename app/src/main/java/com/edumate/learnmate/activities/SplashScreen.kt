package com.edumate.learnmate.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.edumate.learnmate.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val appLogoSplash = findViewById<ImageView>(R.id.splashScreenImg)
        val appNameTxtView = findViewById<com.google.android.material.textview.MaterialTextView>(R.id.splashScreenTxt)
        val footerTxtView = findViewById<com.google.android.material.textview.MaterialTextView>(R.id.splashScreenFooter)

        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_anim)

        appLogoSplash.startAnimation(animation)
        appNameTxtView.startAnimation(animation)
        footerTxtView.startAnimation(animation)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        },2000)
    }
}