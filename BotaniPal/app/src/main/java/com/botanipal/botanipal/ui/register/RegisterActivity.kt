package com.botanipal.loginregisterfirebase.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        // Setup onClickListener untuk tombol register
        register_button.setOnClickListener {
            val username = username.text.toString().trim()
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()
            val confirmPassword = confirm_password.text.toString().trim()

            // Validasi input
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Panggil fungsi register dari ViewModel
            viewModel.register(email, password)
        }

        // Setup onClickListener untuk TextView redirect login
        loginRedirectText.setOnClickListener {
            // Redirect ke halaman login
            // Implementasi sesuai kebutuhan aplikasi Anda
        }
    }
}
