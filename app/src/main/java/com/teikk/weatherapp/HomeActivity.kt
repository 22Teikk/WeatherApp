package com.teikk.weatherapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.teikk.weatherapp.Adapter.WeatherFutureAdapter
import com.teikk.weatherapp.Models.Weather
import com.teikk.weatherapp.Models.WeatherX
import com.teikk.weatherapp.Repository.WeatherRepository
import com.teikk.weatherapp.Utils.Constant
import com.teikk.weatherapp.Utils.Resource
import com.teikk.weatherapp.ViewModels.WeatherViewModel
import com.teikk.weatherapp.ViewModels.WeatherViewModelProviderFactory
import com.teikk.weatherapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var futureAdapter: WeatherFutureAdapter


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        setUpViewModel()
        getCurrentWeatherInfo()
        getForecaseInfo()
    }

    private fun setUpRecyclerView() {
        futureAdapter = WeatherFutureAdapter(listOf())
        binding.rcvViewWeatherFuture.apply {
            adapter = futureAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getForecaseInfo() {
        weatherViewModel.forecastData.observe(this, Observer {respone ->
            when (respone) {
                is Resource.Success -> {
                    hideProgressBar()
                    respone.data?.let {
                        val list = mutableListOf<WeatherX>()
                        for (data in it.list) {
                            list.add(data.weather[0])
                        }
                        futureAdapter.list = list
                        futureAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                else -> {
                    hideProgressBar()
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentWeatherInfo() {
        weatherViewModel.weatherData.observe(this, Observer { respone ->
            when (respone) {
                is Resource.Success -> {
                    hideProgressBar()
                    val weatherData: Weather? = respone.data
                    weatherData?.let {
                        binding.apply {
                            txtCity.text = it.name
                            txtWeather.text = it.weather[0].main
                            txtTime.text = Constant.convertTimeZone(it.timezone)
                            Glide.with(this@HomeActivity)
                                .load(Constant.urlImage(it.weather[0].icon))
                                .into(binding.imgWeather)
                            txtTemperature.text =
                                Constant.kelvinToCelsius(it.main.temp).toString() + "°C"
                            txtHL.text =
                                "H: " + Constant.kelvinToCelsius(it.main.temp_max) + " L: " + Constant.kelvinToCelsius(
                                    it.main.temp_min
                                )
                            txtCloud.text = it.clouds.all.toString() + "%"
                            txtHumidity.text = it.main.humidity.toString() + "%"
                            txtWinSpeed.text = it.wind.speed.toString() + "m/s"
                        }
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Error -> {
                    hideProgressBar()
                }
            }
        })

    }

    private fun setUpViewModel() {
        val weatherRepository = WeatherRepository()
        val viewModelProvider = WeatherViewModelProviderFactory(weatherRepository, application)
        weatherViewModel = ViewModelProvider(this, viewModelProvider)[WeatherViewModel::class.java]
    }

    private fun hideProgressBar() {
        binding.progressBar2.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar2.visibility = View.VISIBLE
    }
}