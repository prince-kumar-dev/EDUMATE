package com.edumate.learnmate.activities

import android.app.Dialog
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edumate.learnmate.R
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var forgetPasswordEmail: com.google.android.material.textfield.TextInputEditText
    private lateinit var forgetPasswordEmailContainer: com.google.android.material.textfield.TextInputLayout
    private lateinit var btnResetPassword: androidx.appcompat.widget.AppCompatButton
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        auth = FirebaseAuth.getInstance()

        setUpViews()

        btnResetPassword.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val email = forgetPasswordEmail.text.toString()

        showProgressBar()

        if (email.isEmpty()) {
            hideProgressBar()
            forgetPasswordEmailContainer.helperText = "Enter your email"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            hideProgressBar()
            forgetPasswordEmailContainer.helperText = "Enter valid email address"
        } else {

            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    hideProgressBar()
                    Toast.makeText(this, "Please check your email", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    hideProgressBar()
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun setUpViews() {
        findViews()
    }

    private fun findViews() {
        forgetPasswordEmail = findViewById(R.id.forgetPasswordEmailIDEditTxt)
        btnResetPassword = findViewById(R.id.resetBtn)
        forgetPasswordEmailContainer = findViewById(R.id.forgetPasswordEmailContainer)
    }

    private fun showProgressBar() {
        dialog = Dialog(this@ForgetPasswordActivity) // Create a new dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // Request no title for the dialog window
        dialog.setContentView(R.layout.progress_bar_layout) // Set layout for the progress dialog
        dialog.setCanceledOnTouchOutside(false) // Set dialog to not dismiss on outside touch
        dialog.show() // Show the progress dialog
    }

    private fun hideProgressBar() {
        dialog.dismiss() // Dismiss the progress dialog
    }
}