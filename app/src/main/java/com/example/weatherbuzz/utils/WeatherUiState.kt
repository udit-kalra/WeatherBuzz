package com.example.weatherbuzz.utils

import com.example.weatherbuzz.repo.ForecastItem

sealed class WeatherUiState {
    object Loading : WeatherUiState()

    data class Success(val data: List<ForecastItem>) : WeatherUiState()

    data class Error(
        val data: List<ForecastItem> = emptyList(),
        val message: String
    ) : WeatherUiState()
}