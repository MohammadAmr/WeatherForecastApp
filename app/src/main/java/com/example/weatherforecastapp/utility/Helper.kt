package com.example.weatherforecastapp.utility

import android.annotation.SuppressLint
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
class Helper {
    companion object{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

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

        fun findDifference(d1: String, d2: String) : Long{
            try {
                val incomingDate: Date = dateFormat.parse(d1)
                val currentDate : Date = dateFormat.parse(d2)
                val diff =  incomingDate.time - currentDate.time
                val seconds = diff / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24
                return (days * 24 * 60 * 60) + (hours * 60 * 60) + (minutes * 60) + seconds

            } catch (e: ParseException) {
                e.printStackTrace()
                return 0
            }
        }

        fun formatDate(_year:Int, _month:Int, _day:Int): String{
            val tmp = _month + 1
            var month = tmp.toString()
            var day   = _day.toString()
            if (month.length == 1) {
                month = "0"+month
            }
            if (day.length == 1) {
                day = "0" + day
            }
            return _year.toString() + "-" + month + "-" + day
        }

        fun formatTime(_hour:Int, _minute:Int) : String {
            var hour = _hour.toString()
            var min  = _minute.toString()
            if (hour.length == 1) hour = "0"+hour
            if (min.length  == 1) min  = "0"+min
            return hour+":"+min
        }
        fun getTempType(unit: String): String {
            var ret : String = "° C"
            when(unit)
            {
                "imperial" -> ret = "° F"
                "default"  -> ret = "° K"
            }
            return ret
        }
        fun getWindSpeedType(unit: String) : String {
            var ret : String = " m/s"
            if(unit == "imperial")
            {
                ret = " m/hr"
            }
            return ret
        }
    }

}