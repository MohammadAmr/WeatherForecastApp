package com.example.weatherapp.model

import android.content.Context
import com.example.weatherapp.localDataSource.LocalSource
import com.example.weatherapp.localDataSource.RoomLocalClass
import com.example.weatherapp.localDataSource.WeatherDatabase
import com.example.weatherapp.network.RemoteSource
import com.example.weatherapp.network.RetrofitHelper
import kotlinx.coroutines.flow.Flow

class Repository(
    private val remoteSource: RemoteSource, private val localSource: LocalSource,
) : RepositoryInterface {

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getRepository(context: Context): Repository {
            return INSTANCE ?: synchronized(this) {
                Repository(
                    RetrofitHelper,
                    RoomLocalClass(WeatherDatabase.getDatabase(context).weatherDao())
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