package com.example.weatherbuzz.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.weatherbuzz.repo.WeatherRepository
import com.example.weatherbuzz.utils.CityPreferences
import com.example.weatherbuzz.utils.WeatherUiState
import com.example.weatherbuzz.repo.ForecastItem
import com.example.weatherbuzz.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val cityPreferences: CityPreferences, application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    val lastCity: Flow<String?> = cityPreferences.lastCity

    fun observeCity() {
        viewModelScope.launch {
            repository.getForecastFlow().collectLatest { data ->
                if (data.isEmpty()) {
                    _uiState.value = WeatherUiState.Loading
                } else {
                    _uiState.value = WeatherUiState.Success(data)
                }
            }
        }
    }

    fun refreshCity(city: String, apiKey: String, isSearch: Boolean=true) {
        if (!NetworkUtils.isInternetAvailable(application)) {
            if (isSearch){
                _uiState.value = WeatherUiState.Error(message = "No internet connection")
            }
            return
        }
        viewModelScope.launch {
            try {
                cityPreferences.saveCity(city)
                _uiState.value = WeatherUiState.Loading
                repository.refreshForecast(city, apiKey)
            } catch (e: Exception) {
                val cached = (uiState.value as? WeatherUiState.Success)?.data ?: emptyList<ForecastItem>()
                _uiState.value = WeatherUiState.Error(cached, e.message ?: "Unknown error")
            }
        }
    }
}
