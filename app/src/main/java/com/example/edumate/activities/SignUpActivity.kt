package com.example.edumate.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.edumate.R
import com.example.edumate.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    // declaring variables
    private var collegeNameList = ArrayList<String>()
    private lateinit var collegeNameAutoCompleteEditText: AutoCompleteTextView
    private lateinit var collegeNameEditTextAdapter: ArrayAdapter<String>
    private lateinit var firestore: FirebaseFirestore

    private lateinit var signUpFullName: com.google.android.material.textfield.TextInputEditText
    private lateinit var signUpEmail: com.google.android.material.textfield.TextInputEditText
    private lateinit var fullNameContainer: com.google.android.material.textfield.TextInputLayout
    private lateinit var emailContainer: com.google.android.material.textfield.TextInputLayout
    private lateinit var passwordContainer: com.google.android.material.textfield.TextInputLayout
    private lateinit var confirmPasswordContainer: com.google.android.material.textfield.TextInputLayout
    private lateinit var collegeNameContainer: com.google.android.material.textfield.TextInputLayout
    private lateinit var signUpPassword: com.google.android.material.textfield.TextInputEditText
    private lateinit var signUpConfirmPassword: com.google.android.material.textfield.TextInputEditText
    private lateinit var signUpCollegeName: AutoCompleteTextView
    private lateinit var signUpBtn: androidx.appcompat.widget.AppCompatButton
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance()

        setUpViews() // Call method to set up views

        // setting on click listener on signup button
        signUpBtn.setOnClickListener {
            registerUser() // Call method to register user when signup button is clicked
        }
    }

    private fun setUpViews() {
        findView() // Call method to find views
        setUpFireStore() // Call method to set up firestore fetching data from the firestore cloud database
        activityChange() // Call method to handle activity transition
        setUpCollegeList() // Call method to set up college list in the autocomplete edit text view
    }

    private fun findView() {
        // Find all necessary views
        signUpFullName = findViewById(R.id.fullNameTxt)
        signUpEmail = findViewById(R.id.emailIDEditTxt)
        signUpPassword = findViewById(R.id.passwordEditTxt)
        signUpConfirmPassword = findViewById(R.id.confirmPasswordEditTxt)
        signUpCollegeName = findViewById(R.id.collegeNameAutoCompleteEditText)
        signUpBtn = findViewById(R.id.signUpBtn)
        fullNameContainer = findViewById(R.id.fullNameContainer)
        emailContainer = findViewById(R.id.emailContainer)
        passwordContainer = findViewById(R.id.passwordContainer)
        confirmPasswordContainer = findViewById(R.id.confirmPasswordContainer)
        collegeNameContainer = findViewById(R.id.collegeNameContainer)
    }

    private fun registerUser() {
        // Retrieve user input data
        val fullName = signUpFullName.text.toString()
        val email = signUpEmail.text.toString()
        val password = signUpPassword.text.toString()
        val confirmPassword = signUpConfirmPassword.text.toString()
        val collegeName = signUpCollegeName.text.toString()

        // Set onFocusChangeListeners to handle helper text visibility
        signUpFullName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                fullNameContainer.helperText = enterFullName()
            }
        }
        signUpEmail.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                emailContainer.helperText = enterEmail()
            }
        }
        signUpPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                passwordContainer.helperText = enterPassword()
            }
        }
        signUpConfirmPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                confirmPasswordContainer.helperText = enterConfirmPassword()
            }
        }
        signUpCollegeName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                collegeNameContainer.helperText = enterCollegeName()
            }
        }

        showProgressBar()

        // Validate user input data
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || collegeName.isEmpty()) {
            // Check if any field is empty and show appropriate helper text
            // Hide progress bar
            // Return from function
            if (fullName.isEmpty()) {
                fullNameContainer.helperText = "Enter your full name"
            }
            if (email.isEmpty()) {
                emailContainer.helperText = "Enter your email"
            }
            if (password.isEmpty()) {
                passwordContainer.helperText = "Enter your password"
            }
            if (confirmPassword.isEmpty()) {
                confirmPasswordContainer.helperText = "Re enter your password"
            }
            if (collegeName.isEmpty()) {
                collegeNameContainer.helperText = "Enter your college name"
            }
            hideProgressBar()
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Check if email is valid and show appropriate helper text
            // Hide progress bar
            // Return from function
            hideProgressBar()
            emailContainer.helperText = "Enter valid email address"
            return
        } else if (password.length < 6) {
            // Check if password is at least 6 characters long and show appropriate helper text
            // Hide progress bar
            // Return from function
            hideProgressBar()
            passwordContainer.helperText = "Enter password more than 5 characters"
            return
        } else if (password != confirmPassword) {
            // Check if password matches confirm password and show appropriate helper text
            // Hide progress bar
            // Return from function
            hideProgressBar()
            confirmPasswordContainer.helperText = "Password not matched, try again"
            return
        } else {
            // If all input data is valid, proceed with user registration
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // If user registration is successful, send email verification
                    auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
                        hideProgressBar()
                        // Show toast message to check email for verification
                        Toast.makeText(
                            this,
                            "Please check your email and verify it",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Save user data to Firebase Realtime Database
                        val databaseRef = auth.currentUser?.let { task1 ->
                            database.reference.child("users").child(task1.uid)
                        }
                        val users = Users(
                            fullName, email, password, collegeName,
                            auth.currentUser?.uid
                        )
                        databaseRef?.setValue(users)?.addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                hideProgressBar()
                                // If user data is saved successfully, navigate to LoginActivity
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                hideProgressBar()
                                // Show error message if user data saving fails
                                Toast.makeText(
                                    this,
                                    "${task2.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }?.addOnFailureListener {
                        hideProgressBar()
                        // Show error message if sending email verification fails
                        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    hideProgressBar()
                    // Show error message if user registration fails
                    Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    // Helper methods to handle input field validation
    private fun enterFullName(): String? {
        val fullName = signUpFullName.text.toString()
        if (fullName.isEmpty()) {
            return "Enter your full name"
        }
        return null
    }

    private fun enterEmail(): String? {
        val email = signUpEmail.text.toString()
        if (email.isEmpty()) {
            return "Enter your email"
        }
        return null
    }

    private fun enterPassword(): String? {
        val password = signUpPassword.text.toString()
        if (password.isEmpty()) {
            return "Enter your password"
        }
        return null
    }

    private fun enterConfirmPassword(): String? {
        val confirmPassword = signUpConfirmPassword.text.toString()
        if (confirmPassword.isEmpty()) {
            return "Re enter your password"
        }
        return null
    }

    private fun enterCollegeName(): String? {
        val collegeName = signUpCollegeName.text.toString()
        if (collegeName.isEmpty()) {
            return "Enter your college name"
        }
        return null
    }

    private fun activityChange() {
        // Handle activity transition from SignUpActivity to LoginActivity
        val loginTxt: TextView = findViewById(R.id.loginTxt)
        loginTxt.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpFireStore() {
        // Fetch college list from Firestore and populate AutoCompleteTextView
        val collectionReference =
            firestore.collection("Colleges List").document("teMaCO78gGSS6ev1xsSQ")
        collectionReference.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot != null) {
                    val temp = querySnapshot.data?.get("noOfColleges").toString()
                    val size = temp.toInt() // total number of colleges
                    var i = 1
                    while (i <= size) {
                        val value = querySnapshot.data?.get("$i").toString()
                        collegeNameList.add(value)
                        i++
                    }
                }
            }
            .addOnFailureListener {
                // Show toast message if fetching college list fails
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setUpCollegeList() {
        // Set up college list in AutoCompleteTextView
        collegeNameAutoCompleteEditText = findViewById(R.id.collegeNameAutoCompleteEditText)
        collegeNameEditTextAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, collegeNameList)
        collegeNameAutoCompleteEditText.threshold = 2
        collegeNameAutoCompleteEditText.setAdapter(collegeNameEditTextAdapter)
    }
    private fun showProgressBar() {
        dialog = Dialog(this@SignUpActivity) // Create a new dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // Request no title for the dialog window
        dialog.setContentView(R.layout.progress_bar_layout) // Set layout for the progress dialog
        dialog.setCanceledOnTouchOutside(false) // Set dialog to not dismiss on outside touch
        dialog.show() // Show the progress dialog
    }

    private fun hideProgressBar() {
        dialog.dismiss() // Dismiss the progress dialog
    }
}
