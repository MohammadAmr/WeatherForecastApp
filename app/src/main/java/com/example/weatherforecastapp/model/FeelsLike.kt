package com.example.weatherforecastapp.model

import com.google.gson.annotations.SerializedName

data class FeelsLike(

    @SerializedName("day") var day: Double,
    @SerializedName("night") var night: Double,
    @SerializedName("eve") var eve: Double,
    @SerializedName("morn") var morn: Double

)