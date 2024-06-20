package com.botanipal.botanipal.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.model.Prediction
import com.botanipal.botanipal.data.response.AddBookmarkResponse
import kotlinx.coroutines.launch

class ResultViewModel(private val repository: UserRepository) : ViewModel() {

    fun addBookmark(id: String, prediction: String, image: String, type: String) {
        viewModelScope.launch {
            repository.addBookmark(id, prediction, image, type)
        }
    }

//    fun deleteBookmark(id: String) {
//        viewModelScope.launch {
//            repository.
//        }
//    }
}