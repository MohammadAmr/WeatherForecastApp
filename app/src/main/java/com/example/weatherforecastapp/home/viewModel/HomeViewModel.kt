package com.example.weatherapp.homeScreen.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.*
import com.example.weatherapp.model.RepositoryInterface
import com.example.weatherforecastapp.utility.LocationLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: RepositoryInterface,
    private val locationLocator: LocationLocator
) : ViewModel() {

    init{
        getCurrentLocation()
    }

    private val _weatherResponse = MutableLiveData<WeatherResponse>()
    val weatherResponse: LiveData<WeatherResponse> = _weatherResponse

    fun fetchDataFromAPI(lat: String, long: String, language: String, units: String) {
        var result: WeatherResponse? = null
        viewModelScope.launch(Dispatchers.IO) {
                        result = repository.getWeather(lat, long, language, units)
                        _weatherResponse.postValue(result!!)
            }
    }

    fun getLocation(): LiveData<Pair<Double, Double>> {
        return locationLocator.locationList
    }

    fun getCurrentLocation() {
        locationLocator.getCurrentLocation()
    }
}