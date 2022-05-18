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
import com.example.weatherapp.model.*
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.utility.Helper
import java.text.SimpleDateFormat
import java.util.*

class HourlyAdapter(private val context: Context) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    var hourly : List<Hourly> = emptyList()
    var tempType : String = "° C"

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val txtHour : TextView
            get() = itemView.findViewById(R.id.txtHour)
        val txtDailyTemp : TextView
            get() = itemView.findViewById(R.id.txtDailyTemp)
        val imgTmpState : ImageView
            get() = itemView.findViewById(R.id.imgTmpState)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.hourly, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("M3lsh", "onBindViewHolder: ")
        val now = hourly.get(position)
        holder.txtDailyTemp.text = now.temp.toInt().toString() + tempType
        holder.txtHour.text = Helper.setTextToDayFromTimeStamp(now.dt, "hh aa")

        Glide.with(context).load("https://openweathermap.org/img/wn/" + now.weather[0].icon + ".png").into(holder.imgTmpState);
    }

    override fun getItemCount(): Int {
        return hourly.size
    }

}

