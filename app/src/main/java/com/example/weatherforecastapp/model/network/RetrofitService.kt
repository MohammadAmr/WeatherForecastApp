package com.example.weatherforecastapp.model.network
import com.example.weatherforecastapp.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val appId = "5f40589c146e66738eacada978281f07"

interface RetrofitService {
    @GET("onecall")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String = "minutley",
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") app_id: String = appId
    ): Response<WeatherResponse>
}