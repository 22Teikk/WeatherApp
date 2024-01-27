package com.teikk.weatherapp.Models.Hour

data class Forecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherHours>,
    val message: Int
)
