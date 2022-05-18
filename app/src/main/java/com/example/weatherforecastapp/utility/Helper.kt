package com.example.weatherforecastapp.utility

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Helper {
    companion object{
        @SuppressLint("SimpleDateFormat")
        fun getDateTime(pattern: String, time: Long): String {
            val sdf = SimpleDateFormat(pattern)
            sdf.timeZone = TimeZone.getTimeZone("GMT+2")
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(Date(time * 1000))
        }


        @SuppressLint("SimpleDateFormat")
        fun setTextToDayFromTimeStamp(time: Long, pattern: String) : String{
            return getDateTime(pattern, time)
        }
    }

}