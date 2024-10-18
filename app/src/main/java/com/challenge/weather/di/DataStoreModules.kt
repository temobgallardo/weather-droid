package com.challenge.weather.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.challenge.weather.data.local.WeatherSerializer
import com.challenge.weather.data.local.WeatherToStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val dataStoreFileName = "com.challenge.weather.datastore"

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModules {

	@Provides
	@Singleton
	fun provideWeatherSerializer(): WeatherSerializer = WeatherSerializer()

	@Provides
	@Singleton
	fun provideWeatherDataStore(
		serializer: WeatherSerializer,
		@ApplicationContext context: Context
	): DataStore<WeatherToStore> = DataStoreFactory.create(
		serializer = serializer,
		produceFile = { context.dataStoreFile(dataStoreFileName) })
}
