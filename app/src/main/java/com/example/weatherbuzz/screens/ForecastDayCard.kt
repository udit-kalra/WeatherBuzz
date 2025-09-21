package com.example.weatherbuzz.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherbuzz.repo.ForecastItem
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.weatherbuzz.R


@Composable
fun ForecastDayCard(day: String, forecasts: List<ForecastItem>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.6f)
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = day,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(forecasts) { forecast ->
                    ForecastHourItem(forecast)
                }
            }
        }
    }
}

@Composable
fun ForecastHourItem(forecast: ForecastItem) {
    val iconRes = when (forecast.iconCode) {
        "01d" -> R.drawable.ic_clear_day
        "01n" -> R.drawable.ic_clear_night
        "02d", "02n" -> R.drawable.ic_partly_cloudy
        "03d", "03n", "04d", "04n" -> R.drawable.ic_cloudy
        "09d", "09n", "10d", "10n" -> R.drawable.ic_rain
        "11d", "11n" -> R.drawable.ic_thunder
        "13d", "13n" -> R.drawable.ic_snow
        "50d", "50n" -> R.drawable.ic_mist
        else -> R.drawable.ic_unknown
    }

    Card(
        modifier = Modifier
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = forecast.time, modifier = Modifier.padding(start = 5.dp))
            Row( verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.temp_icon),
                    contentDescription = "Temp icon",
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "${forecast.temp}°C")
            }
            Row( verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = forecast.iconCode,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = forecast.description)
            }
        }
    }
}


