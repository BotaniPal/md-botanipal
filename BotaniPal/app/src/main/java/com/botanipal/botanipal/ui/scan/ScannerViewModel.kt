package com.botanipal.botanipal.ui.scan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.botanipal.botanipal.data.api.ApiConfig
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.pref.UserModel
import com.botanipal.botanipal.data.pref.UserPreference
import com.botanipal.botanipal.data.response.ScanData
import com.botanipal.botanipal.data.response.ScanResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScannerViewModel(private val repository: UserRepository) : ViewModel() {
    private val _scanType = MutableLiveData<ScanData>()
    val scanType: LiveData<ScanData> = _scanType

    private val _scanDisease = MutableLiveData<ScanData>()
    val scanDisease: LiveData<ScanData> = _scanDisease

    fun scannerType(file: MultipartBody.Part)  {
        repository.scanType(file)
        repository.scanType.observeForever {
            _scanType.value = it
        }
        Log.d("ScanType", _scanType.value.toString())
    }

    fun scannerDisease(file: MultipartBody.Part)  {
        repository.scanDisease(file)
        repository.scanDisease.observeForever {
            _scanDisease.value = it
        }
        Log.d("ScanDisease", _scanDisease.value.toString())
    }



//    companion object {
//        var scanType: ScanResponse = ScanResponse()
//    }
}