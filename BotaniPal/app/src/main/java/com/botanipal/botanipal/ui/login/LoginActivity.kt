package com.botanipal.botanipal.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.botanipal.botanipal.R
import com.botanipal.botanipal.data.pref.UserModel
import com.botanipal.botanipal.ui.MainActivity
import com.botanipal.botanipal.ui.ViewModelFactory
import com.botanipal.botanipal.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth


import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    // td di comment
    private lateinit var auth: FirebaseAuth
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerRedirectText: TextView
    private lateinit var email: String // td di comment
//    private lateinit var uid: String

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

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

        viewModel.getSession().observe(this) { session ->
            if (session.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        // td di comment
        auth = Firebase.auth

        // td di comment
        email = intent.getStringExtra(EXTRA_EMAIL) ?: ""
//        uid = intent.getStringExtra(EXTRA_UID) ?: ""

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

            // td di comment
            auth.signInWithEmailAndPassword(
                email,
                password
            ) // Use username or email here, depending on your Firebase setup
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Login successful.", Toast.LENGTH_SHORT).show()
                        // Navigate to the main activity or another appropriate screen
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Login failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

           viewModel.login(username, password)
            viewModel.loginResult.observe(this) { result ->
                result.onSuccess {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                result.onFailure {
                    Toast.makeText(
                        baseContext,
                        "Login failed: ${it.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
//            viewModel.saveSession(UserModel(username, "sample_token"))
//            viewModel.saveSession(UserModel(username, "sample_token"))
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

    companion object {
        const val EXTRA_EMAIL = "email"
        const val EXTRA_UID = "uid"
    }
}
