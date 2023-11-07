package com.example.group3project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.common.SignInButton
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private lateinit var btnSignUp: SignInButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        btnSignUp = findViewById(R.id.button_signUP)

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }
    }
}