package com.teikk.weatherapp.API

import com.teikk.weatherapp.Models.Weather
import com.teikk.weatherapp.Utils.Constant.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPI {

//    https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
//    Current weather data
    @GET("data/2.5/weather")
    suspend fun getWeatherByCity(
        @Query("q")
        cityName: String = "hanoi",
        @Query("appid")
        apiKey: String = API_KEY
    ): Response<Weather>}

//    api.openweathermap.org/data/2.5/forecast?q={city name}&appid={API key}
//    5 day / 3 hour forecast data
//    @GET("data/2.5/forecast")
//    suspend fun getForecastByCity(
//        @Query("q")
//        cityName: String = "hanoi",
//        @Query("appid")
//        apiKey: String = API_KEY
//    ): Response<Forecast>}