package com.challenge.weather.domain

import com.challenge.weather.domain.models.Weather

interface IWeatherRepository {
	suspend fun getWeatherByCity(city: String): Weather
	suspend fun getWeatherByCityAndCountryCode(city: String, countryCode: String): Weather
	suspend fun getWeatherByCityCountryCodeAndStateCode(
		city: String,
		countryCode: String,
		stateCode: String
	): Weather
}