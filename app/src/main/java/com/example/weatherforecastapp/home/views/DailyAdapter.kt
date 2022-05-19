package com.example.weatherforecastapp.home.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforecastapp.model.*
import com.example.weatherforecastapp.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DailyAdapter(private val context: Context) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    var daily : List<Daily> = emptyList()
    var tempType : String = "° C"

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
         val txtTempState : TextView
            get() = itemView.findViewById(R.id.txtTempState)
        val txtDay       : TextView
            get() = itemView.findViewById(R.id.txtDay)
        val txtMaxMin    : TextView
            get() = itemView.findViewById(R.id.txtManMin)
        val imgTemp      : ImageView
            get() = itemView.findViewById(R.id.imgTemp)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.daily, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val today = daily.get(position)
        holder.txtTempState.text = today.weather[0].description.capitalize()
        holder.txtDay.text = setTextToDayFromTimeStamp(today.dt)
        holder.txtMaxMin.text = today.temp.max.toInt().toString() + "/" + today.temp.min.toInt().toString()

        Glide.with(context).load("https://openweathermap.org/img/wn/" + today.weather[0].icon + ".png").into(holder.imgTemp);
    }

    override fun getItemCount(): Int {
        return daily.size
    }

}

@SuppressLint("SimpleDateFormat")
fun getDateTime(pattern: String, time: Long): String {
    val sdf = SimpleDateFormat(pattern)
    sdf.timeZone = TimeZone.getTimeZone("GMT+2")
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(time * 1000))
}


@SuppressLint("SimpleDateFormat")
fun setTextToDayFromTimeStamp(time: Long) : String{
    return getDateTime("EEE", time)
}
