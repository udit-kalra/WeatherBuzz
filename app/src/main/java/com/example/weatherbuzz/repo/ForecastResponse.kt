package com.example.weatherbuzz.repo

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list") val list: List<ForecastDto>
)

data class ForecastDto(
    @SerializedName("dt_txt") val dtTxt: String,
    @SerializedName("main") val main: MainDto,
    @SerializedName("weather") val weather: List<WeatherDto>
)

data class MainDto(
    @SerializedName("temp") val temp: Double
)

data class WeatherDto(
    @SerializedName("icon") val icon: String,
    @SerializedName("description") val description: String
)
