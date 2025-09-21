package com.example.weatherbuzz.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherbuzz.utils.WeatherUiState
import com.example.weatherbuzz.viewmodel.ForecastViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun HomeScreen(
    viewModel: ForecastViewModel = hiltViewModel(),
    apiKey: String
) {
    var city by remember { mutableStateOf("Noida") }
    var isRefreshed by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    val refreshing = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.lastCity.collect { saved ->
            if (!isRefreshed) {
            isRefreshed = true
            Log.i("CheckCall", "HomeScreen: ")
            val cityToLoad = saved ?: city
            viewModel.observeCity()
            viewModel.refreshCity(cityToLoad, apiKey, false)
            city = cityToLoad
        }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2196F3),
                        Color(0xFFBBDEFB)
                    )
                )
            )
    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                placeholder = { Text("Enter city") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.6f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.6f),
                    disabledContainerColor = Color.White.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Button( shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxHeight(),
                onClick = {
                    keyboardController?.hide()
                    viewModel.refreshCity(city, apiKey)
            }) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {
            is WeatherUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is WeatherUiState.Success -> {
                val forecast = (uiState as WeatherUiState.Success).data
                val grouped = forecast.groupBy { it.date }
                val first3Days = grouped.keys.take(3)

                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = refreshing.value),
                    onRefresh = {
                        refreshing.value = true
                        viewModel.refreshCity(city, apiKey)
                        refreshing.value = false
                    }
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        items(first3Days) { day ->
                            ForecastDayCard(day = day, forecasts = grouped[day] ?: emptyList())
                        }
                    }

                }
            }

            is WeatherUiState.Error -> {
                val state = uiState as WeatherUiState.Error
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Error: ${state.message}", color = Color.Red)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.refreshCity(city, apiKey) }) {
                            Text("Retry")
                        }
                    }
            }
        }
    }

}
}
