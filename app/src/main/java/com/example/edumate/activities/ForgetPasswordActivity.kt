package com.example.edumate.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.edumate.R
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var forgetPasswordEmail: com.google.android.material.textfield.TextInputEditText
    private lateinit var forgetPasswordEmailContainer: com.google.android.material.textfield.TextInputLayout
    private lateinit var btnResetPassword: androidx.appcompat.widget.AppCompatButton
    private lateinit var auth: FirebaseAuth
    private lateinit var forgetPasswordProgressBar: ProgressBar
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

        forgetPasswordProgressBar.visibility = View.VISIBLE

        if (email.isEmpty()) {
            forgetPasswordEmailContainer.helperText = "Enter your email"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgetPasswordProgressBar.visibility = View.GONE
            forgetPasswordEmailContainer.helperText = "Enter valid email address"
        } else {

            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    forgetPasswordProgressBar.visibility = View.GONE
                    Toast.makeText(this, "Please check your email", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    forgetPasswordProgressBar.visibility = View.GONE
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
        forgetPasswordProgressBar = findViewById(R.id.forgetPasswordProgressBar)
        forgetPasswordEmailContainer = findViewById(R.id.forgetPasswordEmailContainer)
    }
}