package com.challenge.weather.data.local

import androidx.datastore.core.Serializer
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

class WeatherSerializer : Serializer<WeatherToStore> {
	override val defaultValue: WeatherToStore
		get() = WeatherToStore()

	override suspend fun readFrom(input: InputStream): WeatherToStore {
		val result = withContext(Dispatchers.IO) {
			input.readBytes()
		}

		val weatherStored = String(result)
		return Gson().fromJson(weatherStored, WeatherToStore::class.java)
	}

	override suspend fun writeTo(t: WeatherToStore, output: OutputStream) {
		val result = Gson().toJson(t)
		withContext(Dispatchers.IO) {
			output.write(result.toByteArray())
		}
	}

}