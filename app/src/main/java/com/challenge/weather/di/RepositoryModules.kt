package com.challenge.weather.di

import com.challenge.weather.data.LocationService
import com.challenge.weather.data.WeatherRepositoryService
import com.challenge.weather.domain.ILocationService
import com.challenge.weather.domain.IWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {
	@Binds
	@Singleton
	abstract fun provideWeatherRepository(implementations: WeatherRepositoryService): IWeatherRepository

	@Binds
	@Singleton
	abstract fun provideLocationService(implementations: LocationService): ILocationService
}