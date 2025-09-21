package com.example.weatherbuzz.local_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val time: String,
    val temp: Double,
    val iconCode: String,
    val description: String,
)
