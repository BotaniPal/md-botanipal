package com.botanipal.botanipal.ui.bookmark

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botanipal.botanipal.data.api.ApiConfig
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.response.BookmarkResponse
import com.botanipal.botanipal.data.response.DataItem
import kotlinx.coroutines.launch

class BookmarkViewModel(private val repository: UserRepository) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _bookmark = MutableLiveData<List<DataItem>>()
    val bookmark: LiveData<List<DataItem>> = _bookmark

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchBookmark(): LiveData<List<DataItem>> {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.getBookmark()
            Log.d("BookmarkViewModel", "Response: $response")
            _bookmark.value = response.data
            _isLoading.value = false
        }
        return bookmark
    }
}