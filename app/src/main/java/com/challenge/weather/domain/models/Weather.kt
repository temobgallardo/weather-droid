package com.challenge.weather.domain.models

// Related to our domain needs (just what's needed)
data class Weather(
	val city: String,
	val description: String,
	val temperature: Double,
	val iconCode: String
)