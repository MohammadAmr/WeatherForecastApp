package com.example.weatherforecastapp.home.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.model.Daily
import com.example.weatherapp.utility.convertNumbersToArabic
import com.example.weatherforecastapp.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DailyAdapter(private val context: Context) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    var daily : List<Daily> = emptyList()
    var tempType : String = ""

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
         val txtTempState : TextView
            get() = itemView.findViewById(R.id.txtTempState)
        val txtDay       : TextView
            get() = itemView.findViewById(R.id.txtDay)
        val txtMaxMin    : TextView
            get() = itemView.findViewById(R.id.txtManMin)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.daily, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val today = daily.get(position + 1)

    }

    override fun getItemCount(): Int {
        return daily.size - 1
    }
}
