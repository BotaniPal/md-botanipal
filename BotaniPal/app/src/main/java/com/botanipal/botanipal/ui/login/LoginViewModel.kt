package com.botanipal.botanipal.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.pref.UserModel
import com.botanipal.botanipal.data.response.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun login( username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                val user = UserModel(response.data?.user?.username ?: "", response.data?.token ?: "",true)
                saveSession(user)
                _loginResult.value = Result.success(response)
                Log.d("LoginViewModel", "Login result: $response")
                Log.d("LoginViewModel", "Session saved: ${response.data?.token}")
            } catch (e: HttpException) {
                _loginResult.value = Result.failure(e)
                Log.d("LoginViewModel", "Login error HttpException: ${e.message} ${e.response()?.errorBody()?.string()}")
            } catch (e:Exception) {
//                val errorResponse = Gson().fromJson(e.message, LoginResponse::class.java)
                _loginResult.value = Result.failure(e)
                Log.d("LoginViewModel", "Login error Exception: $e")
            }
        }
    }

    private fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
            Log.d("LoginViewModel", "Session saved: $user")
        }
    }
}