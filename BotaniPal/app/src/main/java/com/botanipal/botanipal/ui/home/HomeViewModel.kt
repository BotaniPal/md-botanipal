package com.botanipal.botanipal.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.response.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: UserRepository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    fun getWeather(location: String) {
        Log.d("HomeViewModel", "Getting weather data for location: $location")
//        viewModelScope.launch{
        repository.getWeatherData(location)
        repository.weatherData.observeForever { weatherData ->
            _weatherData.value = weatherData
        }
        Log.d("HomeViewModel", "Weather data received: ${_weatherData.value}")
    }
}