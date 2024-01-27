package com.teikk.weatherapp.Models.Hour

import com.teikk.weatherapp.Models.Coord

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)