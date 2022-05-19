package com.example.weatherforecastapp.home.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforecastapp.homeScreen.viewModel.HomeViewModel
import com.example.weatherforecastapp.homeScreen.viewModel.HomeViewModelFactory
import com.example.weatherforecastapp.model.*
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.utility.*

class HomeFragment : Fragment() {

    //TextViews
    private lateinit var txtTemp : TextView
    private lateinit var txtTmpType : TextView
    private lateinit var txtToday : TextView
    private lateinit var txtCity : TextView
    private lateinit var txtPressure : TextView
    private lateinit var txtHumidity : TextView
    private lateinit var txtWindSpeed : TextView
    private lateinit var txtClouds : TextView
    private lateinit var txtUltraviolet : TextView
    private lateinit var txtVisibility : TextView

    //Image Views
    private lateinit var imgTmp : ImageView

    //Recycler Views
    private lateinit var dailyRV  : RecyclerView
    private lateinit var hourlyRV : RecyclerView

    //Adapters
    private lateinit var dailyAdapter  : DailyAdapter
    private lateinit var hourlyAdapter : HourlyAdapter

    //ViewModel
    private lateinit var viewModel : HomeViewModel
    private lateinit var homeFactory : HomeViewModelFactory


    //API Needed data
    private var lat   : Double = 0.0
    private var long  : Double = 0.0
    private var lang  : String = "en"
    private var units : String = "metric"

    //Life Cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initUI(view)
        return view
    }


    //Functions
    private fun initUI(view : View) {
        var sharedPref = activity?.getSharedPreferences("shared", Context.MODE_PRIVATE)

        //TextViews
        txtTemp        = view.findViewById(R.id.txtTemp)
        txtTmpType     = view.findViewById(R.id.txtTmpType)
        txtToday       = view.findViewById(R.id.txtToday)
        txtCity        = view.findViewById(R.id.txtCity)
        txtPressure    = view.findViewById(R.id.txtPressure)
        txtHumidity    = view.findViewById(R.id.txtHumidity)
        txtWindSpeed   = view.findViewById(R.id.txtWindSpeed)
        txtClouds      = view.findViewById(R.id.txtClouds)
        txtUltraviolet = view.findViewById(R.id.txtUV)
        txtVisibility  = view.findViewById(R.id.txtVisibility)

        //ImageViews
        imgTmp         = view.findViewById(R.id.imgTmp)

        //Recylcer Views
        dailyRV        = view.findViewById(R.id.dailyRV)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        dailyAdapter = DailyAdapter(requireActivity())
        dailyRV.apply {
            layoutManager = linearLayoutManager
            adapter = dailyAdapter
        }

        hourlyRV = view.findViewById(R.id.hourlyRV)
        val linearLayoutManager2 = LinearLayoutManager(activity)
        linearLayoutManager2.orientation = RecyclerView.HORIZONTAL
        hourlyAdapter = HourlyAdapter(requireActivity())
        hourlyRV.apply {
            layoutManager = linearLayoutManager2
            adapter = hourlyAdapter
        }


        units = sharedPref?.getString("units", "metric") ?: "metric"
        Log.i("M3lsh", "HomeScreen Units VAR: ${units} ")
        //ViewModels
        homeFactory = HomeViewModelFactory(
            Repository.getRepository(requireActivity().applicationContext), LocationLocator(requireActivity())
        )
        viewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]


        Log.i("M3lsh", "I am here")


        viewModel.getLocation().observe(viewLifecycleOwner) {
            if (it != Pair(0.0, 0.0)) {
                lat  = it.first
                long = it.second
                viewModel.fetchDataFromAPI("$lat", "$long", lang, units)
            }
        }

        viewModel.weatherResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                dailyAdapter.apply {
                    this.daily = it.daily
                    this.tempType = Helper.getTempType(units)
                    notifyDataSetChanged()
                }

                hourlyAdapter.apply{
                    this.hourly = it.hourly
                    this.tempType = Helper.getTempType(units)
                    notifyDataSetChanged()
                }
                txtTemp.text        = it.current.temp.toInt().toString()
                txtTmpType.text     = Helper.getTempType(units)
                txtToday.text       = Helper.setTextToDayFromTimeStamp(it.current.dt, "EEE, dd MMM")
                txtCity.text        = it.timezone
                txtPressure.text    = it.current.pressure.toString()
                txtHumidity.text    = it.current.humidity.toString()
                txtWindSpeed.text   = it.current.windSpeed.toString() + " " + Helper.getWindSpeedType(units)
                txtClouds.text      = it.current.clouds.toString()
                txtUltraviolet.text = it.current.uvi.toString()
                txtVisibility.text  = it.current.visibility.toString()

                Glide.with(this).load("https://openweathermap.org/img/wn/" + it.current.weather[0].icon + ".png").into(imgTmp);
            }
        }
        units = sharedPref?.getString("units", "metric") ?: "metric"
        Log.i("M3lsh", "initUI HomeFrag units = ${units}")
        viewModel.fetchDataFromAPI("$lat","$long",lang, units)


    }

}