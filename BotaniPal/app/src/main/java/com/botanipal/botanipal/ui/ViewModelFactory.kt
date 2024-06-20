package com.botanipal.botanipal.ui

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.di.Injection
import com.botanipal.botanipal.ui.bookmark.BookmarkViewModel
import com.botanipal.botanipal.ui.chat.ChatViewModel
import com.botanipal.botanipal.ui.home.HomeViewModel
import com.botanipal.botanipal.ui.login.LoginViewModel
import com.botanipal.botanipal.ui.price.PriceViewModel
import com.botanipal.botanipal.ui.register.RegisterViewModel
import com.botanipal.botanipal.ui.scan.ResultViewModel
import com.botanipal.botanipal.ui.scan.ScannerViewModel

class ViewModelFactory(private val repository: UserRepository, private val sharedPreferences: SharedPreferences, private val resources: Resources) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ScannerViewModel::class.java)) {
            return ScannerViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(PriceViewModel::class.java)) {
            return PriceViewModel(repository, resources) as T
        } else if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel() as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    val sharedPreferences = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context), sharedPreferences, context.resources)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}