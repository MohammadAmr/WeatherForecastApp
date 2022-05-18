package com.example.weatherapp.utility

import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.example.weatherapp.model.WeatherAlert
import com.example.weatherforecastapp.R
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val nightTime =64800000
const val morningTime =21600000

fun getDateMillis(date: String,language:String): Long {
    val f = SimpleDateFormat("dd/MM/yyyy", Locale(language))
    val d: Date = f.parse(date)
    return d.time
}

 fun getCurrentTime(): Long {
    val hour =
        TimeUnit.HOURS.toMillis(Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toLong())
    val minute =
        TimeUnit.MINUTES.toMillis(Calendar.getInstance().get(Calendar.MINUTE).toLong())

    return (hour + minute)
}

fun getCurrentLocale(context: Context): Locale? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales[0]
    } else {
        context.resources.configuration.locale
    }
}

fun getCityText(context: Context, lat: Double, lon: Double, language: String): String {
    var city = "Unknown!"
    val geocoder = Geocoder(context, Locale(language))
    try {
        val addresses = geocoder.getFromLocation(lat, lon, 1)
        if (addresses.isNotEmpty()) {
            city = "${addresses[0].adminArea}, ${addresses[0].countryName}"
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return city
}


fun convertNumbersToArabic(value: Double): String {
    return (value.toString() + "")
        .replace("1".toRegex(), "١").replace("2".toRegex(), "٢")
        .replace("3".toRegex(), "٣").replace("4".toRegex(), "٤")
        .replace("5".toRegex(), "٥").replace("6".toRegex(), "٦")
        .replace("7".toRegex(), "٧").replace("8".toRegex(), "٨")
        .replace("9".toRegex(), "٩").replace("0".toRegex(), "٠")
}

fun convertNumbersToArabic(value: Int): String {
    return (value.toString() + "")
        .replace("1".toRegex(), "١").replace("2".toRegex(), "٢")
        .replace("3".toRegex(), "٣").replace("4".toRegex(), "٤")
        .replace("5".toRegex(), "٥").replace("6".toRegex(), "٦")
        .replace("7".toRegex(), "٧").replace("8".toRegex(), "٨")
        .replace("9".toRegex(), "٩").replace("0".toRegex(), "٠")
}


