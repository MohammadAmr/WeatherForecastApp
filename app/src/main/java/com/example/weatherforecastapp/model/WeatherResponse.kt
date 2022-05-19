package com.example.weatherforecastapp.model

import com.example.weatherforecastapp.model.Alerts
import com.example.weatherforecastapp.model.Current
import com.example.weatherforecastapp.model.Daily
import com.example.weatherforecastapp.model.Hourly
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("lat") var lat: Double,
    @SerializedName("lon") var lon: Double,
    @SerializedName("timezone") var timezone: String,
    @SerializedName("timezone_offset") var timezoneOffset: Int,
    @SerializedName("current") var current: Current,
    @SerializedName("hourly") var hourly: List<Hourly>,
    @SerializedName("daily") var daily: List<Daily>,
    @SerializedName("alerts") var alerts: List<Alerts>?
)