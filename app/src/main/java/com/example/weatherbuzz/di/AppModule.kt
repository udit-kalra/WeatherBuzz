package com.example.weatherbuzz.di

import android.content.Context
import androidx.room.Room
import com.example.weatherbuzz.local_db.ForecastDao
import com.example.weatherbuzz.local_db.WeatherDatabase
import com.example.weatherbuzz.repo.WeatherApi
import com.example.weatherbuzz.repo.WeatherRepository
import com.example.weatherbuzz.utils.CityPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_db"
        ).build()
    }

    @Provides
    fun provideForecastDao(db: WeatherDatabase): ForecastDao = db.forecastDao()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideCityPreferences(@ApplicationContext context: Context): CityPreferences {
        return CityPreferences(context)
    }

    @Provides @Singleton
    fun provideRepository(
        api: WeatherApi,
        dao: ForecastDao
    ): WeatherRepository = WeatherRepository(api, dao)
}