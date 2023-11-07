package com.example.group3project

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.group3project.models.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.StringBuilder

class Signup : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN: Int = 1
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mAuth: FirebaseAuth
    private lateinit var edtName: EditText
    private lateinit var gmail: TextView
    private lateinit var closeImage: ImageView
    private lateinit var buttonRegister: MaterialButton
    private lateinit var mGoogleSignInAccount: GoogleSignInAccount


    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.

        val user = mAuth.currentUser
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()
        edtName = findViewById(R.id.edit_name)
        gmail = findViewById(R.id.gmail)
        closeImage = findViewById(R.id.image_close)
        buttonRegister = findViewById(R.id.button_register)

        // Configure Google Sign In
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        setListener()

    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception=task.exception

            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                gmail.text = StringBuilder().append(account.email)
                mGoogleSignInAccount = account
            }
            catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun setListener() {
        closeImage.setOnClickListener { onBackPressed() }

        gmail.setOnClickListener {
            signIn()
        }

        buttonRegister.setOnClickListener{
            if (TextUtils.isEmpty(edtName.text.toString())) {
                edtName.error = "Name Required"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(gmail.text.toString()) || !isValidMail(gmail.text.toString())) {
                gmail.error = "Email Empty or not valid"
                return@setOnClickListener
            }
            firebaseAuthWithGoogle()
        }

    }

    private fun isValidMail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun firebaseAuthWithGoogle() {
        val credential = GoogleAuthProvider.getCredential(mGoogleSignInAccount.idToken, null)

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Signup, "Login Failed: ", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onBackPressed() {
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle("Cancel Process?")
            .setMessage("Are you sure want to cancel the registration process?")
            .setPositiveButton("YES") { dialog, which ->
                super.onBackPressed()
            }
            .setNegativeButton("NO") { dialog, which -> dialog.dismiss() }
        builder.show()
    }


}