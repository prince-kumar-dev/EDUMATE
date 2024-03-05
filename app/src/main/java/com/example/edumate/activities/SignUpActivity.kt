package com.example.edumate.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
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
    private lateinit var signUpProgressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance()

        setUpViews()
        // setting on click listener on signup button
        signUpBtn.setOnClickListener {
            registerUser()
        }
    }

    private fun setUpViews() {
        findView() // finding the views
        setUpFireStore() // set up firestore fetching data from the firestore cloud database
        activityChange() // intent from one one activity to other
        setUpCollegeList() // setting up college list in the autocomplete edit text view
    }

    private fun findView() {
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
        signUpProgressBar = findViewById(R.id.progressBarSignUp)
    }

    private fun registerUser() {
        val fullName = signUpFullName.text.toString()
        val email = signUpEmail.text.toString()
        val password = signUpPassword.text.toString()
        val confirmPassword = signUpConfirmPassword.text.toString()
        val collegeName = signUpCollegeName.text.toString()

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

        signUpProgressBar.visibility = View.VISIBLE

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || collegeName.isEmpty()) {
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
            signUpProgressBar.visibility = View.GONE
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpProgressBar.visibility = View.GONE
            emailContainer.helperText = "Enter valid email address"
        } else if (password.length < 6) {
            signUpProgressBar.visibility = View.GONE
            passwordContainer.helperText = "Enter password more than 5 characters"
        } else if (password != confirmPassword) {
            signUpProgressBar.visibility = View.GONE
            confirmPasswordContainer.helperText = "Password not matched, try again"
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    // Verifying the User Email
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnSuccessListener {
                            signUpProgressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                "Please check your email and verify it",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Saving Data of User
                            val databaseRef =
                                auth.currentUser?.let { task1 ->
                                    database.reference.child("users").child(task1.uid)
                                }

                            val users = Users(
                                fullName, email, password, collegeName,
                                auth.currentUser?.uid
                            )
                            databaseRef?.setValue(users)?.addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    signUpProgressBar.visibility = View.GONE
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    signUpProgressBar.visibility = View.GONE
                                    Toast.makeText(
                                        this,
                                        "${task2.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        ?.addOnFailureListener {
                            signUpProgressBar.visibility = View.GONE
                            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                        }
                } else {
                    signUpProgressBar.visibility = View.GONE
                    Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

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
        val loginTxt: TextView = findViewById(R.id.loginTxt)

        loginTxt.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpFireStore() {

        // Fetching College List in AutoCompleteTextView
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
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }

    private fun setUpCollegeList() {
        collegeNameAutoCompleteEditText = findViewById(R.id.collegeNameAutoCompleteEditText)
        collegeNameEditTextAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, collegeNameList)
        collegeNameAutoCompleteEditText.threshold = 2
        collegeNameAutoCompleteEditText.setAdapter(collegeNameEditTextAdapter)
    }
}