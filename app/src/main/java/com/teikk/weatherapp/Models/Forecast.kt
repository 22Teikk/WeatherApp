package com.teikk.weatherapp.Models

data class Forecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastList>,
    val message: Int
)