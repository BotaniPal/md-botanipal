package com.botanipal.botanipal.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botanipal.botanipal.data.api.ApiConfig
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.response.DataItem
import com.botanipal.botanipal.data.response.ScanData
import kotlinx.coroutines.launch

class BookmarkTabViewModel(private val repository: UserRepository) : ViewModel() {
    private val _plantType = MutableLiveData<List<ScanData>>()
    val plantType : LiveData<List<ScanData>> = _plantType

    private val _isLoadingType = MutableLiveData<Boolean>()
    val isLoadingType : LiveData<Boolean> = _isLoadingType

    private val _plantDisease = MutableLiveData<List<ScanData>>()
    val plantDisease : LiveData<List<ScanData>> = _plantDisease

    private val _isLoadingDisease = MutableLiveData<Boolean>()
    val isLoadingDisease : LiveData<Boolean> = _isLoadingDisease

    private val apiService = ApiConfig.getApiService()
//    private lateinit var plantAdapter: BookmarkPlantAdapter

//    private val _chat = MutableLiveData<List<ScanData>>()
//    fun getBookmark() : LiveData<List<DataItem>> {
//        viewModelScope.launch {
//            repository.getBookmark()
//        }
//    }
}