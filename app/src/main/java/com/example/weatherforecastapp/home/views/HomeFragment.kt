package com.example.weatherforecastapp.home.views

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
import com.example.weatherapp.homeScreen.viewModel.HomeViewModel
import com.example.weatherapp.homeScreen.viewModel.HomeViewModelFactory
import com.example.weatherapp.model.*
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.utility.*
import java.util.*

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
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initUI(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    //Functions
    private fun initUI(view : View)
    {
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

        //ViewModels
        homeFactory = HomeViewModelFactory(
            Repository.getRepository(requireActivity().applicationContext), LocationLocator(requireActivity())
        )
        viewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]

        viewModel.getLocation().observe(viewLifecycleOwner) {
            if (it != Pair(0.0, 0.0)) {
                lat  = it.first
                long = it.second
                viewModel.fetchDataFromAPI("$lat", "$long", lang, units)
                Log.i("M3lsh", "initUI: observerLocation")
            }
        }

        viewModel.weatherResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                dailyAdapter.apply {
                    this.daily = it.daily
                    this.tempType = "° C"
                    notifyDataSetChanged()
                    Log.i("M3lsh", "initUI: weatherResponse")
                }

                hourlyAdapter.apply{
                    this.hourly = it.hourly
                    this.tempType = "° C"
                    notifyDataSetChanged()
                }
                txtTemp.text        = it.current.temp.toInt().toString()
                txtToday.text       = Helper.setTextToDayFromTimeStamp(it.current.dt, "EEE, dd MMM")
                txtCity.text        = it.timezone
                txtPressure.text    = it.current.pressure.toString()
                txtHumidity.text    = it.current.humidity.toString()
                txtWindSpeed.text   = it.current.windSpeed.toString()
                txtClouds.text      = it.current.clouds.toString()
                txtUltraviolet.text = it.current.uvi.toString()
                txtVisibility.text  = it.current.visibility.toString()

                Glide.with(this).load("https://openweathermap.org/img/wn/" + it.current.weather[0].icon + ".png").into(imgTmp);
            }
        }

        viewModel.fetchDataFromAPI("$lat","$long",lang, units)


    }
}