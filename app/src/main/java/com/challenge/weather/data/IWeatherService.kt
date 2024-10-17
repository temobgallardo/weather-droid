package com.challenge.weather.data

import com.challenge.weather.data.models.CityWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

const val weatherApiKey = "15c08d55dca35af18244780fa1a0f047"

interface IWeatherService {
	@GET("weather")
	suspend fun getWeatherByCity(
		@Query("q") query: String,
		// TODO: replace by interceptor on HttClient
		@Query("appid") apiKey: String = weatherApiKey
	): CityWeatherDto

}