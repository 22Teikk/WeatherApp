package com.teikk.weatherapp.Models.Hour

import com.teikk.weatherapp.Models.Clouds
import com.teikk.weatherapp.Models.WeatherX
import com.teikk.weatherapp.Models.Wind

data class WeatherHours(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<WeatherX>,
    val wind: Wind
)