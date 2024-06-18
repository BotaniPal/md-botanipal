package com.botanipal.loginregisterfirebase.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.botanipal.loginregisterfirebase.R
import com.botanipal.loginregisterfirebase.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Setup onClickListener untuk tombol login
        login_button.setOnClickListener {
            val username = username.text.toString().trim()
            val password = password.text.toString().trim()

            // Panggil fungsi login dari ViewModel
            viewModel.login(username, password)
        }

        // Observasi perubahan state dari ViewModel
        viewModel.loginState.observe(this, Observer { loginState ->
            when (loginState) {
                LoginViewModel.LoginState.Loading -> {
                    // Tampilkan progress atau animasi loading jika diperlukan
                }
                LoginViewModel.LoginState.Success -> {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    // Redirect ke halaman utama atau halaman berikutnya setelah login berhasil
                    // Implementasi sesuai kebutuhan aplikasi Anda
                }
                is LoginViewModel.LoginState.Error -> {
                    Toast.makeText(this, "Login failed: ${loginState.errorMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        // Setup onClickListener untuk TextView redirect register
        registerRedirectText.setOnClickListener {
            // Redirect ke halaman registrasi
            navigateToRegister()
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}
