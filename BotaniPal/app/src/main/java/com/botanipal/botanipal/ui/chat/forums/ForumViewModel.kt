package com.botanipal.botanipal.ui.chat.forums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.pref.UserModel

class ForumViewModel(private val repository: UserRepository) : ViewModel(){
    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> = _index

    fun setIndex(index: Int){
        _index.value = index
    }

    fun getSession(): LiveData<UserModel>{
        return repository.getSession().asLiveData()
    }
}