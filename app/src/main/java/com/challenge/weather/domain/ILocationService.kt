package com.challenge.weather.domain

import kotlinx.coroutines.flow.Flow

// Defines a way to get a user current city
interface ILocationService {
	suspend fun getCurrentCityName(): Flow<String>
}
