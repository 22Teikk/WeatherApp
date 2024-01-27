package com.teikk.weatherapp.Utils

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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

        object HideKeyboard {
            @SuppressLint("ClickableViewAccessibility")
            fun setupUI(view: View, activity: Activity) {
                if (view !is EditText) {
                    view.setOnTouchListener { _, _ ->
                        hideSoftKeyboard(activity)
                        false
                    }
                }

                if (view is ViewGroup) {
                    for (i in 0 until view.childCount) {
                        val innerView: View = view.getChildAt(i)
                        setupUI(innerView, activity)
                    }
                }
            }

            private fun hideSoftKeyboard(activity: Activity) {
                val inputMethodManager =
                    activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                val currentFocus = activity.currentFocus
                currentFocus?.let {
                    inputMethodManager.hideSoftInputFromWindow(
                        it.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }
            }
        }
    }
}

