package com.teikk.weatherapp.Repository

import com.teikk.weatherapp.API.RetrofitInstance

class WeatherRepository {
    suspend fun getWeatherByCity(cityName: String) = RetrofitInstance.api.getWeatherByCity(cityName)

    suspend fun getForecastByCity(cityName: String) = RetrofitInstance.api.getForecastByCity(cityName)
}