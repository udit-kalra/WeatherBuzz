package com.example.weatherbuzz.utils

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.text.SimpleDateFormat

fun convertToAmPm(time: String): String {
    // Input format: "18:00:00"
    val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    val date = inputFormat.parse(time)  // Convert string -> Date
    return outputFormat.format(date)    // Convert Date -> formatted string
}

