package com.example.weatherbuzz.repo


import com.example.weatherbuzz.local_db.ForecastDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val dao: ForecastDao
) {
    fun getForecastFlow(): Flow<List<ForecastItem>> =
        dao.getAll().map { it.map { entity -> entity.toDomain() } }

    suspend fun refreshForecast(city: String, apiKey: String) {
        dao.clearAll()
        val response = api.getForecast(city, apiKey)
        val items = response.list.map { it.toDomain() }
        dao.insertAll(items.map { it.toEntity() })
    }
}