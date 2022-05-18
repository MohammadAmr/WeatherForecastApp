package com.example.weatherapp.localDataSource

import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.model.WeatherAlert
import kotlinx.coroutines.flow.Flow

class RoomLocalClass(private val weatherDao: WeatherDao) : LocalSource {

    override fun getCurrentWeather(): WeatherResponse {
        return weatherDao.getCurrentWeather()
    }

    override fun getFavoriteWeather(id: Int): WeatherResponse {
        return weatherDao.getFavoriteWeather(id)
    }

    override suspend fun insertAlert(alert: WeatherAlert):Long {
        return weatherDao.insertAlert(alert)
    }

    override fun getAlertsList(): Flow<List<WeatherAlert>> {
        return weatherDao.getAlertsList()
    }

    override suspend fun deleteAlert(id: Int) {
        weatherDao.deleteAlert(id)
    }

    override suspend fun insertCurrentWeather(weather: WeatherResponse):Long {
        return weatherDao.insertWeather(weather)
    }

    override suspend fun updateWeather(weather: WeatherResponse) {
        weatherDao.updateWeather(weather)
    }

    override suspend fun deleteWeathers() {
        weatherDao.deleteCurrentWeather()
    }

    override fun getFavoritesWeather(): Flow<List<WeatherResponse>> {
        return weatherDao.getFavoritesWeather()
    }

    override suspend fun deleteFavoriteWeather(id: Int) {
        weatherDao.deleteFavoriteWeather(id)
    }

    override fun getAlert(id: Int): WeatherAlert {
        return weatherDao.getAlert(id)
    }
}