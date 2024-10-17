package com.challenge.weather.di

import com.challenge.weather.data.IWeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {

	@Provides
	@Singleton
	// TODO: add retry interval, etc.
	fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

	@Provides
	@Singleton
	fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
		  Retrofit.Builder().baseUrl(weatherApiOp)
			  .addConverterFactory(GsonConverterFactory.create()).build()

	@Provides
	@Singleton
	fun provideWeatherService(retrofitService: Retrofit): IWeatherService =
		  retrofitService.create(IWeatherService::class.java)
}

const val weatherApiOp = "https://api.openweathermap.org/data/2.5/"