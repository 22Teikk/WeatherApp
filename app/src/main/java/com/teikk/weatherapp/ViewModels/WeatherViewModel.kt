package com.teikk.weatherapp.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teikk.weatherapp.Models.Hour.Forecast
import com.teikk.weatherapp.Models.Weather
import com.teikk.weatherapp.Repository.WeatherRepository
import com.teikk.weatherapp.Utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel(private val weatherRepository: WeatherRepository, application: Application) :
    AndroidViewModel(application) {

    val weatherData: MutableLiveData<Resource<Weather>> = MutableLiveData()
    val forecastData: MutableLiveData<Resource<Forecast>?> = MutableLiveData()
    init {
        getWeather("hanoi")
        getForecast("hanoi")
    }
    fun getWeather(city: String) = viewModelScope.launch {
        weatherData.postValue(Resource.Loading())
        val weather = weatherRepository.getWeatherByCity(city)
        weatherData.postValue(handleWeatherResponse(weather))
    }

    private fun handleWeatherResponse(response: Response<Weather>): Resource<Weather> {
        return when (response.isSuccessful) {
            true -> Resource.Success(response.body()!!)
            false -> Resource.Error(response.message())
        }
    }

    fun getForecast(city: String) = viewModelScope.launch {
        forecastData.postValue(Resource.Loading())
        val forecast = weatherRepository.getForecastByCity(city)
        forecastData.postValue(handleForecastResponse(forecast))
    }

    private fun handleForecastResponse(forecast: Response<Forecast>): Resource<Forecast> {
        return when (forecast.isSuccessful) {
            true -> Resource.Success(forecast.body()!!)
            false -> Resource.Error(forecast.message())
        }
    }
}