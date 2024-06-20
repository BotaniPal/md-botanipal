package com.botanipal.botanipal.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.botanipal.botanipal.R
import com.botanipal.botanipal.databinding.ActivityRegisterBinding
import com.botanipal.botanipal.ui.ViewModelFactory
import com.botanipal.botanipal.ui.login.LoginActivity
import com.botanipal.botanipal.ui.login.LoginActivity.Companion.EXTRA_EMAIL
import com.botanipal.botanipal.ui.login.LoginActivity.Companion.EXTRA_UID
import com.botanipal.botanipal.ui.login.LoginViewModel
import com.botanipal.botanipal.ui.register.RegisterViewModel.Companion.uidUser

//import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var response: String
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi ViewModel
//        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        // Setup onClickListener untuk tombol register
        binding.registerButton.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val confirmPassword = binding.comfirmPassword.text.toString().trim()

            Log.d("RegisterActivity", "username: $username, email: $email, password: $password, confirmPassword: $confirmPassword")

            // Validasi input
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (username.length < 4) {
                Toast.makeText(this, "Username must be at least 4 characters long.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Panggil fungsi register dari ViewModel
//            viewModel.registerFirebase(email, password)
            viewModel.register(username, email, password, confirmPassword)
            uid = uidUser
//            viewModel.saveSession(viewModel.uid, username, "sample_token")
        }

        // Observasi perubahan state dari ViewModel
        viewModel.registrationState.observe(this) { registrationState ->
            when (registrationState) {
                RegisterViewModel.RegistrationState.Loading -> {
                    // Tampilkan progress atau animasi loading jika diperlukan
                }

                RegisterViewModel.RegistrationState.Success -> {
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    // Redirect ke halaman login atau halaman utama aplikasi setelah registrasi berhasil
                    navigateToLogin()
                }

                is RegisterViewModel.RegistrationState.Error -> {
                    Toast.makeText(
                        this,
                        "Registration failed: ${registrationState.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Setup onClickListener untuk TextView redirect login
        binding.loginRedirectText.setOnClickListener {
            // Redirect ke halaman login
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra(EXTRA_EMAIL, binding.email.text.toString())
            putExtra(EXTRA_UID, uid)
        }
        startActivity(intent)
        finish()
    }
}
