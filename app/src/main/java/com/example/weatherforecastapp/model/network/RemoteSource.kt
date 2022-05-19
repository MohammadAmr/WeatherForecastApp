package com.example.weatherforecastapp.model.network

import com.example.weatherforecastapp.model.*
import retrofit2.Response

interface RemoteSource {
    suspend fun getCurrentWeather(lat: String, long: String, language: String, units: String): Response<WeatherResponse>
}