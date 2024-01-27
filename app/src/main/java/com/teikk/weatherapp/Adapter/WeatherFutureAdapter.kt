package com.teikk.weatherapp.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teikk.weatherapp.Models.Hour.Forecast
import com.teikk.weatherapp.Models.WeatherX
import com.teikk.weatherapp.Utils.Constant
import com.teikk.weatherapp.databinding.WeatherItemBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class WeatherFutureAdapter(var list: List<WeatherX>): RecyclerView.Adapter<WeatherFutureAdapter.WeatherFutureViewHolder>() {
    inner class WeatherFutureViewHolder(val binding: WeatherItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherFutureViewHolder {
        return WeatherFutureViewHolder(
            WeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherFutureViewHolder, position: Int) {
        val weatherX = list[position]
        holder.binding.apply {
            txtDes.text = printHourAndRoundedMinutes(position)
            txtMain.text = weatherX.description
            Glide.with(this.root).load(Constant.urlImage(weatherX.icon)).into(imgWeather)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun printHourAndRoundedMinutes(position: Int): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")

        val newTime = now.plus((position * 3).toLong(), ChronoUnit.HOURS)
        val roundedTime = newTime.withMinute(0).withSecond(0)

        if (position == 0 || (position > 0 && position * 3 < 24 && roundedTime.dayOfMonth == now.dayOfMonth)) {
            return "Today\n " + roundedTime.format(formatter)
        } else {
            // Check if it's tomorrow or a future date
            val dayDifference = ChronoUnit.DAYS.between(now.toLocalDate(), roundedTime.toLocalDate())
            return when {
                dayDifference == 1L -> "Tomorrow " + roundedTime.format(formatter)
                else -> roundedTime.format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))
            }
        }
    }
}