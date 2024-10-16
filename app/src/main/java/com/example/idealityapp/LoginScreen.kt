package com.example.idealityapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginScreen : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var btnLogin: Button
    lateinit var etEmailLogin: EditText
    lateinit var etPasswordLogin: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_screen)

        auth = FirebaseAuth.getInstance()
        btnLogin = findViewById(R.id.btnLogin)
        etEmailLogin = findViewById(R.id.etEmailLogin)
        etPasswordLogin = findViewById(R.id.etPasswordLogin)
        auth.signOut() // Log out any user before showing login screen

        // Handle insets to avoid UI overlap with system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Handle login button click
        btnLogin.setOnClickListener {
            loginUser()
        }

        // Clear the password hint when focused and restore it when unfocused
        etPasswordLogin.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                etPasswordLogin.hint = ""  // Clear the hint
            } else {
                etPasswordLogin.hint = getString(R.string.password_info)  // Show the hint again
            }
        }
    }

    private fun loginUser() {
        val email = etEmailLogin.text.toString().trim()
        val password = etPasswordLogin.text.toString().trim()

        // Input validation before login
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.length < 6) {
            Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }

        // Disable login button during the process
        btnLogin.isEnabled = false

        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                withContext(Dispatchers.Main) {
                    checkLoggedInState() // Proceed to the next screen
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginScreen, e.message, Toast.LENGTH_LONG).show()
                    btnLogin.isEnabled = true // Re-enable button after failure
                }
            }
        }
    }

    // Go to Signup screen
    fun SignupScreen(view: View?) {
        val intent = Intent(this@LoginScreen, SignupScreen::class.java)
        startActivity(intent)
    }

    // Check if the user is already logged in
    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }

    // Check login state and proceed to the next screen
    private fun checkLoggedInState() {
        if (auth.currentUser != null) {
            // Clear email and password fields
            etEmailLogin.text.clear()
            etPasswordLogin.text.clear()

            // Navigate to the next screen (e.g., BusinessOwner activity)
            startActivity(Intent(this@LoginScreen, BusinessOwner::class.java))
            finish() // Close the LoginScreen activity
        } else {
            btnLogin.isEnabled = true // Re-enable button if not logged in
        }
    }
}
