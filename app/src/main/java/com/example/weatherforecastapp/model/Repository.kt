package com.example.weatherapp.model

import android.content.Context
import com.example.weatherapp.network.RemoteSource
import com.example.weatherapp.network.RetrofitHelper

class Repository(
    private val remoteSource: RemoteSource) : RepositoryInterface {

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getRepository(context: Context): Repository {
            return INSTANCE ?: synchronized(this) {
                Repository(
                    RetrofitHelper
                ).also {
                    INSTANCE = it
                }
            }
        }
    }

    override suspend fun getWeather(
        lat: String, long: String, language: String, units: String): WeatherResponse {
        val result = remoteSource.getCurrentWeather(lat, long, language, units)
        return if (result.isSuccessful) {
            result.body()!!
        } else {
            throw Exception("${result.errorBody()}")
        }
    }

}