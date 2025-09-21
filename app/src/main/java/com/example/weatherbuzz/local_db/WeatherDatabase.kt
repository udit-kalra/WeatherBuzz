package com.example.weatherbuzz.local_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ForecastEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
}