package com.example.weatherforecastapp.home.views

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.homeScreen.viewModel.HomeViewModel
import com.example.weatherapp.homeScreen.viewModel.HomeViewModelFactory
import com.example.weatherapp.model.MyLocationProvider
import com.example.weatherapp.model.Repository
import com.example.weatherapp.model.*
import com.example.weatherapp.utility.ConnectivityChecker
import com.example.weatherforecastapp.R

class HomeFragment : Fragment() {

    //TextViews
    private lateinit var txtTemp : TextView
    private lateinit var txtTmpType : TextView
    private lateinit var txtCity : TextView
    private lateinit var txtPressure : TextView
    private lateinit var txtHumidity : TextView
    private lateinit var txtWindSpeed : TextView
    private lateinit var txtClouds : TextView
    private lateinit var txtUltraviolet : TextView
    private lateinit var txtVisibility : TextView

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
        txtCity        = view.findViewById(R.id.txtCity)
        txtPressure    = view.findViewById(R.id.txtPressure)
        txtHumidity    = view.findViewById(R.id.txtHumidity)
        txtWindSpeed   = view.findViewById(R.id.txtWindSpeed)
        txtClouds      = view.findViewById(R.id.txtClouds)
        txtUltraviolet = view.findViewById(R.id.txtUV)
        txtVisibility  = view.findViewById(R.id.txtVisibility)

        //Recylcer Views
        dailyRV        = view.findViewById(R.id.dailyRV)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        dailyAdapter = DailyAdapter(activity)
        dailyRV.apply {
            layoutManager = linearLayoutManager
            adapter = dailyAdapter
        }

        hourlyRV       = view.findViewById(R.id.hourlyRV)
        val linearLayoutManager2 = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        hourlyAdapter = HourlyAdapter(activity)
        hourlyRV.apply {
            layoutManager = linearLayoutManager2
            adapter = hourlyAdapter
        }


        //ViewModels
        homeFactory = HomeViewModelFactory(
            Repository.getRepository(activity!!.applicationContext), MyLocationProvider(activity!!)
        )
        viewModel = ViewModelProvider(this,homeFactory)[HomeViewModel::class.java]
        activity?.registerReceiver(ConnectivityChecker(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

}