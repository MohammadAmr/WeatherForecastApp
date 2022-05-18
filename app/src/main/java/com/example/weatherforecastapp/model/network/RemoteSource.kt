package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response

interface RemoteSource {
    suspend fun getCurrentWeather(lat: String, long: String, language: String, units: String): Response<WeatherResponse>
}