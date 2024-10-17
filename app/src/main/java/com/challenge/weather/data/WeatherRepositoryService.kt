package com.challenge.weather.data

import com.challenge.weather.domain.IWeatherRepository
import com.challenge.weather.domain.models.Weather
import javax.inject.Inject

class WeatherRepositoryService @Inject constructor(private val weatherServiceApi: IWeatherService) :
	IWeatherRepository {

	override suspend fun getWeatherByCity(city: String): Weather {
		val response = weatherServiceApi.getWeatherByCity(city)
		return response.toDomain()
	}

	override suspend fun getWeatherByCityAndCountryCode(city: String, countryCode: String): Weather {
		val query = "$city,$countryCode"
		val response = weatherServiceApi.getWeatherByCity(query)
		return response.toDomain()
	}

	override suspend fun getWeatherByCityCountryCodeAndStateCode(
		city: String,
		countryCode: String,
		stateCode: String
	): Weather {
		val query = "$city,$stateCode,$countryCode"
		val response = weatherServiceApi.getWeatherByCity(query)
		return response.toDomain()
	}
}