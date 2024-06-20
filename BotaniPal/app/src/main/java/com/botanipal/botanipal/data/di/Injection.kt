package com.botanipal.botanipal.data.di

import android.content.Context
import com.botanipal.botanipal.BuildConfig
import com.botanipal.botanipal.data.api.ApiService
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.pref.UserPreference
import com.botanipal.botanipal.data.pref.dataStore
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = provideUserPreference(context)
        val retrofit = provideRetrofit()
        val apiService = provideApiService(retrofit)
        return UserRepository.getInstance(apiService,pref)
    }

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    private fun provideUserPreference(context: Context): UserPreference {
        return UserPreference.getInstance(context.dataStore)
    }
}