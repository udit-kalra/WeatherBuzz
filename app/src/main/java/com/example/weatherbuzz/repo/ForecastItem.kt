package com.example.weatherbuzz.repo

data class ForecastItem(
    val date: String,
    val time: String,
    val temp: Double,
    val iconCode: String,
    val description: String
)
