package com.challenge.weather.data

import com.challenge.weather.data.models.CityWeatherDto
import com.challenge.weather.domain.models.Weather

// This helps us transform our API models to our domain models
// This could be done using
fun CityWeatherDto.toDomain(): Weather =
	  Weather(
		  this.name ?: "",
		  this.weather?.firstOrNull()?.description ?: "",
		  this.main?.temp ?: 0.0,
		  this.weather?.firstOrNull()?.icon ?: ""
	  )
