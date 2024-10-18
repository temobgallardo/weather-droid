package com.challenge.weather

import com.challenge.weather.domain.ILocationService
import com.challenge.weather.domain.IWeatherRepository
import com.challenge.weather.domain.models.Weather
import com.challenge.weather.viewModels.WeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

//@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class WeatherViewModelTest {

	@Mock
	private lateinit var weatherRepository: IWeatherRepository

	@Mock
	private lateinit var locationService: ILocationService

	private lateinit var weatherViewModel: WeatherViewModel

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		weatherViewModel = WeatherViewModel(weatherRepository, locationService)
	}

	@Test
	fun test_getWeather_updates_weather_state() = runTest {
		val weather = Weather("Test City", "", 25.0, "")
		`when`(weatherRepository.getWeatherByCity("Test City")).thenReturn(weather)

		weatherViewModel.updateSearchTerm("Test City")
		weatherViewModel.getWeather()

//		verify(weatherRepository).getWeatherByCity("Test City")
		assert(weatherViewModel.weather.value == weather)
	}

//	@Test
//	fun test_getWeatherByUsersLocation_updates_weather_state() = runTest {
//		val weather = Weather("User City", "", 20.0, "")
//		`when`(locationService.getCurrentCityName()).thenReturn(flowOf("User City"))
//		`when`(weatherRepository.getWeatherByCity("User City")).thenReturn(weather)
//
//		weatherViewModel.getWeatherByUsersLocation()
//
//		verify(locationService).getCurrentCityName()
//		verify(weatherRepository).getWeatherByCity("User City")
//		assert(weatherViewModel.weather.value == weather)
//	}
}