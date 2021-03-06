package com.example.weatherforecastapp.model.network
import com.example.weatherforecastapp.model.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper: RemoteSource {
    private const val baseUrl = "https://api.openweathermap.org/data/2.5/"

    private val retrofitService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    override suspend fun getCurrentWeather(
        lat: String,long: String,language: String,units: String): Response<WeatherResponse> =
        retrofitService.getCurrentWeather(lat, long, lang = language, units =units)

}