package com.teikk.weatherapp.Utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Constant {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org"
        const val API_KEY = "d100c9cf618408a44cd60c85200fd629"

        @RequiresApi(Build.VERSION_CODES.O)
        fun convertTimeZone(offsetSeconds: Int): String {
            val sourceZoneId = ZoneId.ofOffset("GMT", ZoneOffset.ofTotalSeconds(offsetSeconds))
            val sourceDateTime = ZonedDateTime.now(sourceZoneId)
            val vietnamZoneId = ZoneId.of("Asia/Ho_Chi_Minh")
            val vietnamDateTime = sourceDateTime.withZoneSameInstant(vietnamZoneId)
            val formatter = DateTimeFormatter.ofPattern("EEE MMM dd| hh:mm a")
            return vietnamDateTime.format(formatter)
        }

        fun kelvinToCelsius(kelvin: Double): Double {
            return kelvin - 273.15
        }

        fun urlImage(imageName: String): String {
            return "https://openweathermap.org/img/wn/"+ imageName +"@2x.png"
        }
    }
}