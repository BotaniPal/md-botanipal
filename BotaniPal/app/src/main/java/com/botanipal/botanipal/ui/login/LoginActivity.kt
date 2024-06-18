package com.botanipal.loginregister.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.botanipal.loginregister.R
import com.botanipal.loginregister.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerRedirectText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Set listener for window insets to adjust padding for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        usernameEditText = findViewById(R.id.username) // Make sure ID is correct in your layout
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.register_button) // Make sure ID is correct in your layout
        registerRedirectText = findViewById(R.id.registerRedirectText)

        // Input Validation: Enable the login button only when both fields are not empty
        usernameEditText.doOnTextChanged { text, _, _, _ -> validateInput() }
        passwordEditText.doOnTextChanged { text, _, _, _ -> validateInput() }

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString() // You might not need the username for Firebase auth
            val password = passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(username, password) // Use username or email here, depending on your Firebase setup
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Login successful.", Toast.LENGTH_SHORT).show()
                        // Navigate to the main activity or another appropriate screen
                    } else {
                        Toast.makeText(baseContext, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        registerRedirectText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInput() {
        loginButton.isEnabled = usernameEditText.text.toString().isNotBlank() &&
                passwordEditText.text.toString().isNotBlank()
    }
}
