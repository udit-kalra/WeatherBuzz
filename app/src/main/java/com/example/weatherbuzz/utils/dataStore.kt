// data/local/CityPreferences.kt
package com.example.weatherbuzz.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("settings")

class CityPreferences(private val context: Context) {
    companion object {
        private val LAST_CITY = stringPreferencesKey("last_city")
    }

    val lastCity: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[LAST_CITY]
    }

    suspend fun saveCity(city: String) {
        context.dataStore.edit { prefs ->
            prefs[LAST_CITY] = city
        }
    }
}
