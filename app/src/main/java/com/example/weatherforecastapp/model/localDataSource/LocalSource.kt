package com.example.weatherapp.localDataSource

import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.model.WeatherAlert
import kotlinx.coroutines.flow.Flow

interface LocalSource {

    fun getCurrentWeather(): WeatherResponse

    suspend fun insertCurrentWeather(weather: WeatherResponse):Long

    suspend fun updateWeather(weather: WeatherResponse)

    suspend fun deleteWeathers()

    fun getFavoritesWeather(): Flow<List<WeatherResponse>>

    suspend fun deleteFavoriteWeather(id:Int)

    fun getFavoriteWeather(id:Int): WeatherResponse

    suspend fun insertAlert(alert: WeatherAlert):Long

    fun getAlertsList(): Flow<List<WeatherAlert>>

    suspend fun deleteAlert(id: Int)

    fun getAlert(id: Int): WeatherAlert

}