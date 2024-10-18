package com.challenge.weather.domain

import com.challenge.weather.domain.models.Weather

// Help us define a way to obtain data or save data for our app,
// decoupling from specific implementation
interface IWeatherRepository {
	suspend fun getWeatherByCity(city: String): Weather
	suspend fun getWeatherByCityAndCountryCode(city: String, countryCode: String): Weather
	suspend fun getWeatherByCityCountryCodeAndStateCode(
		city: String,
		countryCode: String,
		stateCode: String
	): Weather

	suspend fun saveLastWeather(weather: Weather)
	suspend fun getLastWeather(): Weather?
}