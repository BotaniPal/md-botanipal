package com.botanipal.botanipal.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.pref.UserModel
import com.botanipal.botanipal.data.response.RegisterResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val _registrationState = MutableLiveData<RegistrationState>()
    val registrationState: LiveData<RegistrationState>
        get() = _registrationState

    fun register(username: String, email: String, password: String, confirmPassword: String)  {
        _registrationState.value = RegistrationState.Loading
        viewModelScope.launch {
            try{
                Log.d("RegisterViewModel", "Calling repository.register with username: $username, email: $email")
                val response = repository.register(username, email, password, confirmPassword)
                Log.d("RegisterViewModel", "Register response: $response")

//                try {
//                auth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            _registrationState.value = RegistrationState.Success
//                            uidUser = auth.currentUser?.uid.toString()
//                        } else {
//                            val errorMessage = (task.exception as? FirebaseAuthException)?.message
//                            _registrationState.value = RegistrationState.Error(errorMessage ?: "Registration failed.")
//                        }
//                    }
//                }
//            catch (e: FirebaseAuthException) {
//                    _registrationState.value = RegistrationState.Error(e.message ?: "Registration failed.")
//                    Log.e("RegisterViewModel - firebase", "Error registering user: ${e.message}")
//                }

//                saveSession(UserModel(uidUser, _, email, " ", false))

            } catch (e: HttpException) {
                _registrationState.value = RegistrationState.Error(e.message ?: "Registration failed.")
                Log.e("RegisterViewModel-http", "Error registering user: ${e.message} ${e.cause} ${e.stackTrace}")

            }  catch (e: Exception) {
                _registrationState.value = RegistrationState.Error(e.message ?: "Registration failed.")
                Log.e("RegisterViewModel", "Error registering user: ${e.message}")
            }
        }
    }

//    fun register(username: String, email: String, password: String, confirmPassword: String) {
//        _registrationState.value = RegistrationState.Loading
//
//        viewModelScope.launch {
//            try {
//                repository.register(username, email, password, confirmPassword)
//                _registrationState.value = RegistrationState.Success
//            } catch (e: Exception) {
//                _registrationState.value = RegistrationState.Error(e.message ?: "Registration failed.")
//            }
//        }
//
//    }

    private fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    sealed class RegistrationState {
        object Loading : RegistrationState()
        object Success : RegistrationState()
        data class Error(val errorMessage: String) : RegistrationState()
    }

    companion object {
        var uidUser: String = ""
    }
}


