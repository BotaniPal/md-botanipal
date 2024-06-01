package com.botanipal.botanipal.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.botanipal.botanipal.ui.bookmark.BookmarkViewModel
import com.botanipal.botanipal.ui.chat.ChatViewModel
import com.botanipal.botanipal.ui.home.HomeViewModel
import com.botanipal.botanipal.ui.price.PriceViewModel
import com.botanipal.botanipal.ui.scan.ScannerViewModel

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel() as T
        } else if (modelClass.isAssignableFrom(ScannerViewModel::class.java)) {
            return ScannerViewModel() as T
        } else if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel() as T
        } else if (modelClass.isAssignableFrom(PriceViewModel::class.java)) {
            return PriceViewModel() as T
        } else if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (instance == null) {
                synchronized(ViewModelFactory::class.java) {
                    instance = ViewModelFactory(application)
                }
            }
            return instance as ViewModelFactory
        }
    }
}