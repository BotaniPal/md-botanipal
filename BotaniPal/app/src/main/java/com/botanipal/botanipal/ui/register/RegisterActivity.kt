package com.botanipal.loginregister.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
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
import com.botanipal.loginregister.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginRedirectText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Untuk tampilan edge-to-edge
        setContentView(R.layout.activity_register)

        // Set listener for window insets to adjust padding for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        auth = Firebase.auth

        usernameEditText = findViewById(R.id.username)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.comfirm_password) // Pastikan ID ini sesuai dengan layout Anda
        registerButton = findViewById(R.id.register_button)
        loginRedirectText = findViewById(R.id.loginRedirectText)

        usernameEditText.doOnTextChanged { text, _, _, _ -> validateInput() }
        emailEditText.doOnTextChanged { text, _, _, _ -> validateInput() }
        passwordEditText.doOnTextChanged { text, _, _, _ -> validateInput() }
        confirmPasswordEditText.doOnTextChanged { text, _, _, _ -> validateInput() }

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateInput()) { // Lakukan validasi sebelum mendaftarkan pengguna
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, "Registration successful.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(baseContext, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        loginRedirectText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (usernameEditText.text.toString().isBlank()) {
            usernameEditText.error = "Username is required"
            isValid = false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()) {
            emailEditText.error = "Invalid email address"
            isValid = false
        }

        if (passwordEditText.text.toString().length < 6) {
            passwordEditText.error = "Password must be at least 6 characters"
            isValid = false
        }

        if (passwordEditText.text.toString() != confirmPasswordEditText.text.toString()) {
            confirmPasswordEditText.error = "Passwords do not match"
            isValid = false
        }

        return isValid
    }
}
