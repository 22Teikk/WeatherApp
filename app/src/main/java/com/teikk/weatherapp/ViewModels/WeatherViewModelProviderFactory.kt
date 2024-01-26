package com.teikk.weatherapp.ViewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.teikk.weatherapp.Repository.WeatherRepository

class WeatherViewModelProviderFactory(
    private val weatherRepository: WeatherRepository,
    private val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherRepository, app) as T
    }
}