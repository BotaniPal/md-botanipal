package com.botanipal.botanipal.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botanipal.botanipal.data.api.ApiConfig
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.response.BookmarkResponse
import kotlinx.coroutines.launch

class BookmarkViewModel(private val repository: UserRepository) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _bookmark = MutableLiveData<BookmarkResponse>()
    val bookmark: LiveData<BookmarkResponse> = _bookmark

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchBookmark(): LiveData<BookmarkResponse> {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.getBookmark()
            _bookmark.value = response
            _isLoading.value = false
        }
        return bookmark
    }
}