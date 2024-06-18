package com.botanipal.botanipal.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.botanipal.botanipal.data.model.Prediction
import com.botanipal.botanipal.data.pref.UserModel
import com.botanipal.botanipal.data.pref.UserPreference
import com.botanipal.botanipal.data.response.AddBookmarkResponse
import com.botanipal.botanipal.data.response.BookmarkResponse
import com.botanipal.botanipal.data.response.LoginResponse
import com.botanipal.botanipal.data.response.ProfileData
import com.botanipal.botanipal.data.response.ProfileResponse
import com.botanipal.botanipal.data.response.RegisterResponse
import com.botanipal.botanipal.data.response.ScanResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    private var token: String? = null
    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun register(name: String, email: String, password: String, confirmPassword: String): RegisterResponse {
        return apiService.registerUser(name, email, password, confirmPassword)
    }

    suspend fun login(username: String, password: String): LoginResponse {
        return apiService.loginUser(username, password)
    }

    suspend fun getProfile(): ProfileResponse {
//        token = runBlocking {
//            userPreference.getSession().firstOrNull()?.token
//        }
        Log.d("userrepository", "Token getstories: $token")
        return apiService.getUserProfile()
    }

    suspend fun updateProfile(name: String, email: String, password: String, image: MultipartBody.Part): ProfileResponse {
//        token = runBlocking {
//            userPreference.getSession().firstOrNull()?.token
//        }
        Log.d("userrepository", "Token getstories: $token")
        return apiService.updateUserProfile(name, email, password, image)
    }

    suspend fun scanType(image: MultipartBody.Part): ScanResponse {

        return apiService.uploadTypeImage(image)
    }

    suspend fun scanDisease(image: MultipartBody.Part): ScanResponse {

        return apiService.uploadDiseaseImage(image)
    }

    suspend fun getBookmark():BookmarkResponse {
        return apiService.getBookmarks()
    }

    suspend fun addBookmark(predictionId: String, prediction: String, imageUrl: String, predictionType: String ): AddBookmarkResponse {
        return apiService.bookmarkPlant(predictionId, prediction, imageUrl, predictionType)
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }

}