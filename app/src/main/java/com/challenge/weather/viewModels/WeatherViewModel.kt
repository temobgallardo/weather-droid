package com.challenge.weather.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.weather.domain.ILocationService
import com.challenge.weather.domain.IWeatherRepository
import com.challenge.weather.domain.models.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
	private val weatherRepository: IWeatherRepository,
	private val locationService: ILocationService
) :
	ViewModel() {
	private val _searchTerm = mutableStateOf("")
	val searchTerm: State<String>
		get() = _searchTerm


	private val _weather = mutableStateOf<Weather?>(null)
	val weather: State<Weather?>
		get() = _weather


	fun updateSearchTerm(searchTerm: String) {
		_searchTerm.value = searchTerm
	}

	fun getWeather() {
		viewModelScope.launch(Dispatchers.IO) {
			if (_searchTerm.value.isEmpty()) return@launch

			try {
				val result = weatherRepository.getWeatherByCity(_searchTerm.value)
				Log.d("WEATHER APP RESULT", result.toString())
				updateWeather(result)

				Log.d("DATA_STORE", "Saving last weather to the data store: $result")
				saveLastCityAndWeatherToDataStore()
			} catch (e: HttpException) {
				Log.d("Network Request Error", e.message.toString())
			} catch (e: Exception) {
				Log.d("Error", e.message.toString())
			}
		}
	}

	fun getWeatherByUsersLocation() {
		viewModelScope.launch(Dispatchers.IO) {
			locationService.getCurrentCityName().collectLatest {
				updateSearchTerm(it)
				getWeather()
			}
		}
	}

	fun getLastCityAndWeather() {
		viewModelScope.launch(Dispatchers.IO) {
			val lastWeather = weatherRepository.getLastWeather()
			updateWeather(lastWeather)
			updateSearchTerm(lastWeather?.city ?: "")
		}
	}

	private fun saveLastCityAndWeatherToDataStore() {
		viewModelScope.launch(Dispatchers.IO) {
			_weather.value?.let {
				weatherRepository.saveLastWeather(it)
			}
		}
	}

	private fun updateWeather(weather: Weather?) {
		_weather.value = weather
	}
}