package com.example.stadiums

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION


        val intent = Intent(this, MainActivity::class.java)
        val emailField = findViewById<EditText>(R.id.editTextEmail)
        val passwordField = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.LogIn)
        val signUpButton = findViewById<Button>(R.id.signUp)


        signUpButton.setOnClickListener {
            val auth = Firebase.auth
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        // Du kan navigera till MainActivity här om du vill
                        startActivity(intent)
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            Toast.makeText(baseContext, "This email is already in use.",
                                Toast.LENGTH_SHORT).show()
                        } else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(baseContext, "The email address is badly formatted or the password is incorrect.",
                                Toast.LENGTH_SHORT).show()
                        } else if (task.exception is FirebaseAuthInvalidUserException) {
                            Toast.makeText(baseContext, "There is no user record corresponding to this email.",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }

                    }
                }
        }
        loginButton.setOnClickListener {
            val auth = Firebase.auth
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        // Du kan navigera till MainActivity här
                        startActivity(intent)
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(baseContext, "The email address is badly formatted or the password is incorrect.",
                                Toast.LENGTH_SHORT).show()
                        } else if (task.exception is FirebaseAuthInvalidUserException) {
                            Toast.makeText(baseContext, "There is no user record corresponding to this email.",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }
}