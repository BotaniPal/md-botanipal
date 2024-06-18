package com.botanipal.botanipal.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.pref.UserModel
import com.botanipal.botanipal.data.response.ErrorResponse
import com.botanipal.botanipal.data.response.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel (private val repository: UserRepository): ViewModel(){
    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult


    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                val user = UserModel(response.data?.user?.username ?: "", response.data?.token?: "", true)
                saveSession(user)
                _loginResult.value = Result.success(response)
            } catch (e: HttpException) {
                val errorResponse = Gson().fromJson(e.response()?.errorBody()?.string(), ErrorResponse::class.java)
                _loginResult.value = Result.failure(Exception(errorResponse.message))
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e)
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}