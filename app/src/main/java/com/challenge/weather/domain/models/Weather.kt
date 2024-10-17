package com.challenge.weather.domain.models

data class Weather(
	val city: String,
	val description: String,
	val temperature: Double,
	val iconCode: String
)