package com.edumate.fellowmate.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edumate.fellowmate.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailContainer: com.google.android.material.textfield.TextInputLayout
    private lateinit var loginEmail: com.google.android.material.textfield.TextInputEditText
    private lateinit var passwordContainer: com.google.android.material.textfield.TextInputLayout
    private lateinit var loginPassword: com.google.android.material.textfield.TextInputEditText
    private lateinit var loginBtn: androidx.appcompat.widget.AppCompatButton
    private lateinit var forgetPassword: TextView
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        setUpViews() // Call method to set up views

        // Navigate to ForgetPasswordActivity when forget password text is clicked
        forgetPassword.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpViews() {
        findView() // Call method to find views
        activityChange() // Call method to handle activity transition
        userLogin() // Call method to handle user login process
    }

    private fun userLogin() {
        loginBtn.setOnClickListener {
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()

            // Set onFocusChangeListeners to handle helper text visibility
            loginEmail.setOnFocusChangeListener { _, focused ->
                if (!focused) {
                    emailContainer.helperText = enterEmail()
                }
            }
            loginPassword.setOnFocusChangeListener { _, focused ->
                if (!focused) {
                    passwordContainer.helperText = enterPassword()
                }
            }

            showProgressBar()

            // Validate user input data
            if (email.isEmpty() || password.isEmpty()) {
                if (email.isEmpty()) {
                    hideProgressBar()
                    emailContainer.helperText = "Enter your email"
                }
                if (password.isEmpty()) {
                    hideProgressBar()
                    passwordContainer.helperText = "Enter your password"
                }
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // Check if email is valid and show appropriate helper text
                hideProgressBar()
                emailContainer.helperText = "Enter valid email address"
            } else {
                // Attempt to sign in user with provided email and password
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // checking whether user is verified or not
                        val verification = auth.currentUser?.isEmailVerified

                        if (verification == true) {
                            // If user is verified, show success message and navigate to MainActivity
                            hideProgressBar()
                            navigateToMainActivity()
                        } else {
                            // If user is not verified, show message to verify email
                            hideProgressBar()
                            Toast.makeText(this, "Please verify your email", Toast.LENGTH_LONG)
                                .show()
                        }
                    } else {
                        // If sign in fails, show error message
                        hideProgressBar()
                        Toast.makeText(
                            this,
                            "Something went wrong with email or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    // Helper methods to validate user input fields
    private fun enterEmail(): String? {
        val email = loginEmail.text.toString()
        if (email.isEmpty()) {
            return "Enter your email"
        }
        return null
    }

    private fun enterPassword(): String? {
        val password = loginPassword.text.toString()
        if (password.isEmpty()) {
            return "Enter your password"
        }
        return null
    }

    private fun activityChange() {
        // Handle activity transition from LoginActivity to SignUpActivity
        val signUpTxt: TextView = findViewById(R.id.signUpTxt)
        signUpTxt.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun findView() {
        // Find necessary views
        emailContainer = findViewById(R.id.loginEmailContainer)
        loginEmail = findViewById(R.id.loginEmailIDEditTxt)
        passwordContainer = findViewById(R.id.loginPasswordContainer)
        loginPassword = findViewById(R.id.loginPasswordEditTxt)
        loginBtn = findViewById(R.id.loginBtn)
        forgetPassword = findViewById(R.id.forgetPasswordTxt)
    }

    private fun navigateToMainActivity() {
        // Navigate to MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(
            this,
            "Login Successfully",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    // Check if the user is already logged in when activity starts
    override fun onStart() {
        super.onStart()
        val verification = auth.currentUser?.isEmailVerified
        if (auth.currentUser != null && verification == true) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showProgressBar() {
        dialog = Dialog(this@LoginActivity) // Create a new dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // Request no title for the dialog window
        dialog.setContentView(R.layout.progress_bar_layout) // Set layout for the progress dialog
        dialog.setCanceledOnTouchOutside(false) // Set dialog to not dismiss on outside touch
        dialog.show() // Show the progress dialog
    }

    private fun hideProgressBar() {
        dialog.dismiss() // Dismiss the progress dialog
    }
}