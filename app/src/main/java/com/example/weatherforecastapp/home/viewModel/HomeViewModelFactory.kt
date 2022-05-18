package com.example.weatherapp.homeScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastapp.utility.LocationLocator
import com.example.weatherapp.model.RepositoryInterface

class HomeViewModelFactory (private val repository: RepositoryInterface,
    private val locationLocator: LocationLocator
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository, locationLocator) as T
    }
}