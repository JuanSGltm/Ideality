package com.example.idealityapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await

class SignupScreen : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var tvLoggedIn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_signup_screen)

        // Initialize the tvLoggedIn TextView
        tvLoggedIn = findViewById(R.id.tvLoggedIn)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind btnRegister button
        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = findViewById<EditText>(R.id.etEmailRegister).text.toString()
        val password = findViewById<EditText>(R.id.etPasswordRegister).text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInState()
                        goToLoggedInScreen()  // Redirect to the logged-in screen after registration
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SignupScreen, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkLoggedInState() {
        if (auth.currentUser == null) { // Not logged in
            tvLoggedIn.text = "You are not logged in"
        } else {
            tvLoggedIn.text = "You are logged in!"
        }
    }

    private fun goToLoggedInScreen() {
        val intent = Intent(this@SignupScreen, BusinessOwner::class.java)
        startActivity(intent)
        finish() // Optionally close SignupScreen so it is removed from the backstack
    }

    fun LoginScreen(view: View?) {
        val intent = Intent(this@SignupScreen, LoginScreen::class.java)
        startActivity(intent)
    }

    fun SignupScreenBusiness(view: View?) {
        val intent = Intent(this@SignupScreen, SignupScreenBusiness::class.java)
        startActivity(intent)
    }
}
