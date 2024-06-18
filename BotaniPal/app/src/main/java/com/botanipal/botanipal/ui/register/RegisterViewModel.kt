package com.botanipal.loginregisterfirebase.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class RegisterViewModel : ViewModel() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val _registrationState = MutableLiveData<RegistrationState>()
    val registrationState: LiveData<RegistrationState>
        get() = _registrationState

    fun register(email: String, password: String) {
        _registrationState.value = RegistrationState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _registrationState.value = RegistrationState.Success
                } else {
                    val errorMessage = (task.exception as? FirebaseAuthException)?.message
                    _registrationState.value = RegistrationState.Error(errorMessage ?: "Registration failed.")
                }
            }
    }

    sealed class RegistrationState {
        object Loading : RegistrationState()
        object Success : RegistrationState()
        data class Error(val errorMessage: String) : RegistrationState()
    }
}


