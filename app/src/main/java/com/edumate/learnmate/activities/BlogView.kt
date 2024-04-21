package com.edumate.learnmate.activities

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.edumate.learnmate.R

class BlogView : AppCompatActivity() {

    private lateinit var blogWebView: WebView
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)

        blogWebView = findViewById(R.id.blogWebView)
        blogWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                hideProgressBar()
            }
        }

        val url = intent.getStringExtra("URL")
        if (url != null) {
            showProgressBar()
            blogWebView.loadUrl(url)
        }

        blogWebView.settings.javaScriptEnabled = true
        blogWebView.settings.setSupportZoom(true)

    }

    private fun showProgressBar() {
        dialog = Dialog(this@BlogView) // Create a new dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // Request no title for the dialog window
        dialog.setContentView(R.layout.progress_bar_layout) // Set layout for the progress dialog
        dialog.setCanceledOnTouchOutside(false) // Set dialog to not dismiss on outside touch
        dialog.show() // Show the progress dialog
    }

    private fun hideProgressBar() {
        dialog.dismiss() // Dismiss the progress dialog
    }
}