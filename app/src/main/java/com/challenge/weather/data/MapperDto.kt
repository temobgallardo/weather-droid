package com.challenge.weather.data

import com.challenge.weather.data.models.CityWeatherDto
import com.challenge.weather.domain.models.Weather

fun CityWeatherDto.toDomain(): Weather =
	  Weather(
		  this.name ?: "",
		  this.weather?.firstOrNull()?.description ?: "",
		  this.main?.temp ?: 0.0,
		  this.weather?.firstOrNull()?.icon ?: ""
	  )
