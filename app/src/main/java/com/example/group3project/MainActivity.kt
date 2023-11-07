package com.example.group3project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.common.SignInButton
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var btnSignUp: SignInButton
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()

        FirebaseApp.initializeApp(this)
        btnSignUp = findViewById(R.id.button_signUP)

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val user = mAuth.currentUser
        if (user != null) {
            // TODO move to products activity
            val intent = Intent(this, ProductsActivity::class.java)
            startActivity(intent)
        }
    }
}