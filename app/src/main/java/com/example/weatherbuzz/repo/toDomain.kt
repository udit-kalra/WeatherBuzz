package com.example.weatherbuzz.repo

import com.example.weatherbuzz.local_db.ForecastEntity
import com.example.weatherbuzz.utils.convertToAmPm

fun ForecastDto.toDomain(): ForecastItem {
    val dateTimeParts = dtTxt.split(" ")
    val date = dateTimeParts[0]
    val time = convertToAmPm(dateTimeParts[1])
    val iconCode = weather.firstOrNull()?.icon ?: "01d"
    val description = weather.firstOrNull()?.description?: " "

    return ForecastItem(
        date = date,
        time = time,
        temp = main.temp,
        iconCode = iconCode,
        description = description
    )
}

// Both ForecastItem & ForecastEntity is same, but it is better to make separate class for diff purpose
fun ForecastItem.toEntity() = ForecastEntity(
    date = date,
    time = time,
    temp = temp,
    iconCode = iconCode,
    description = description
)

fun ForecastEntity.toDomain() = ForecastItem(
    date = date,
    time = time,
    temp = temp,
    iconCode = iconCode,
    description = description
)
