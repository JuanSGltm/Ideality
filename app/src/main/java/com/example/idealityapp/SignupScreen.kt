package com.example.idealityapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignupScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun LoginScreen(view: View?) {
        val intent = Intent(
            this@SignupScreen,
            LoginScreen::class.java
        )
        startActivity(intent)
    }
    fun SignupScreenBusiness(view: View?) {
        val intent = Intent(
            this@SignupScreen,
            SignupScreenBusiness::class.java
        )
        startActivity(intent)
    }
}