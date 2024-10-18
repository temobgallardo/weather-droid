package com.challenge.weather.viewModels

import com.challenge.weather.domain.ILocationService
import com.challenge.weather.domain.IWeatherRepository
import com.challenge.weather.domain.models.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class WeatherViewModelTest {
	private val dispatcher = StandardTestDispatcher()

	@Mock
	lateinit var weatherRepository: IWeatherRepository

	@Mock
	lateinit var locationService: ILocationService

	lateinit var weatherViewModel: WeatherViewModel

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		Dispatchers.setMain(dispatcher);
		weatherViewModel = WeatherViewModel(weatherRepository, locationService)
	}

	@Test
	fun test_getWeather_updates_weather_state() = runTest {
		val weather = Weather("Test City", "", 25.0, "")
		`when`(weatherRepository.getWeatherByCity("Test City")).thenReturn(weather)

		weatherViewModel.updateSearchTerm("Test City")
		weatherViewModel.getWeather()

		advanceUntilIdle()

		verify(weatherRepository).getWeatherByCity("Test City")
		assert(weatherViewModel.weather.value == weather)
	}

	@Test
	fun test_getWeatherByUsersLocation_updates_weather_state() =
		  runTest {
			  val weather = Weather("User City", "", 20.0, "")
			  `when`(locationService.getCurrentCityName()).thenReturn(flowOf("User City"))
			  `when`(weatherRepository.getWeatherByCity("User City")).thenReturn(weather)

			  weatherViewModel.getWeatherByUsersLocation()
			  advanceUntilIdle()

			  verify(locationService).getCurrentCityName()
			  verify(weatherRepository).getWeatherByCity("User City")
			  assert(weatherViewModel.weather.value == weather)
		  }
}