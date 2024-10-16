package com.example.idealityapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignupScreenBusiness : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup_screen_business)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun LoginScreen(view: View?) {
        val intent = Intent(
            this@SignupScreenBusiness,
            LoginScreen::class.java
        )
        startActivity(intent)
    }
    fun SignupScreen(view: View?) {
        val intent = Intent(
            this@SignupScreenBusiness,
            SignupScreen::class.java
        )
        startActivity(intent)
    }
}