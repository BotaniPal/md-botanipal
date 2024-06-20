package com.botanipal.botanipal.ui.scan

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.botanipal.botanipal.data.api.ApiConfig
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.helper.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class CameraTypeViewModel(private val repository: UserRepository) : ViewModel() {

//    fun scan()
}