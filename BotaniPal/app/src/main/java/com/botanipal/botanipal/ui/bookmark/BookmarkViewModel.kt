package com.botanipal.botanipal.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookmarkViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> = _index

    fun setIndex(index: Int){
        _index.value = index
    }
}