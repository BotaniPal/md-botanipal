package com.botanipal.botanipal.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.botanipal.botanipal.R
import com.botanipal.botanipal.data.pref.UserModel
import com.botanipal.botanipal.databinding.ActivityLoginBinding
import com.botanipal.botanipal.ui.MainActivity
import com.botanipal.botanipal.ui.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var myButton: Button
    private lateinit var passwordEditText: EditText
    private lateinit var emailEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myButton = binding.loginButton
        passwordEditText = binding.passwordEditText
        emailEditText = binding.emailEditText

        setupView()
        setupAction()
        playAnimation()
//        setMyButtonEnable()


//        passwordEditText.addTextChangedListener(onTextChanged = { s, start, before, after ->
//            setMyButtonEnable()
//        })
//
//
//        emailEditText.addTextChangedListener( afterTextChanged = { s ->
//            setMyButtonEnable()
//        })

        myButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(email, password)
            showLoading(true)
        }

        viewModel.loginResult.observe(this) { result ->
            result.onSuccess {
                showLoading(false)
                val color = ContextCompat.getColor(this, R.color.black)
                val title = SpannableString("Yeah!")
                title.setSpan(TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(color), null), 0, title.length, 0)
                val message = SpannableString("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
                message.setSpan(TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(color), null), 0, message.length, 0)

                AlertDialog.Builder(this).apply {
                    setTitle(title)
                    setMessage(message)
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            }

            result.onFailure {
                showToast(it.message.toString())
            }
        }
    }

//    private fun setMyButtonEnable() {
//        val isEmailValid = emailEditText.text.toString().isValidEmail()
//        val isPasswordNotEmpty = passwordEditText.text.toString().length >= 8
//        myButton.isEnabled = isEmailValid && isPasswordNotEmpty
//    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        Toast.makeText(this, passwordEditText.text, Toast.LENGTH_SHORT).show()
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            viewModel.saveSession(UserModel(email, "sample_token"))
            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
                setPositiveButton("Lanjut") { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}