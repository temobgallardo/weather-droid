package com.challenge.weather.data

import androidx.datastore.core.DataStore
import com.challenge.weather.data.local.WeatherToStore
import com.challenge.weather.domain.IWeatherRepository
import com.challenge.weather.domain.models.Weather
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class WeatherRepositoryService @Inject constructor(
	private val weatherServiceApi: IWeatherService,
	private val dataStore: DataStore<WeatherToStore>
) :
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

	override suspend fun saveLastWeather(weather: Weather) {
		val weatherToStore = WeatherToStore(weather)
		dataStore.updateData { weatherToStore }
	}

	override suspend fun getLastWeather(): Weather? {
		val lastWeather = dataStore.data.first()

		return lastWeather.weather
	}
}