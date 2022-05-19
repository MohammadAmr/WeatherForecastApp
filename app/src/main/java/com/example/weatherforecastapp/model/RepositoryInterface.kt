package com.example.weatherforecastapp.model

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun getWeather(
        lat: String, long: String, language: String = "en", units: String = "metric"
    ): WeatherResponse

}