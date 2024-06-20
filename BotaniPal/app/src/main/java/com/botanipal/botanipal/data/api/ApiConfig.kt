package com.botanipal.botanipal.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    fun getDiseaseApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val authInterceptor = Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type", "multipart/form-data")
                .build()
            chain.proceed(newRequest)
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Example: Increase timeout to 30 seconds
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://botanipal-backend-r52fo4vtbq-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun getTypeApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val authInterceptor = Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type", "multipart/form-data")
                .build()
            chain.proceed(newRequest)
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Example: Increase timeout to 30 seconds
            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://botanipal-backend-r52fo4vtbq-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun getWeatherApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val client = OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS) // Example: Increase timeout to 30 seconds
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun getApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
//        val authInterceptor = Interceptor { chain ->
//            val newRequest = chain.request().newBuilder()
//                .addHeader("Authorization", "Bearer $token")
//                .build()
//            chain.proceed(newRequest)
//        }
        val client = OkHttpClient.Builder()
            .connectTimeout(100L, TimeUnit.SECONDS) // Example: Increase timeout to 30 seconds
            .readTimeout(100L, TimeUnit.SECONDS)
            .writeTimeout(100L, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://botanipal-backend-r52fo4vtbq-et.a.run.app"  )
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}