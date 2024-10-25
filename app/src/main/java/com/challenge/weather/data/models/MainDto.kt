package com.challenge.weather.data.models


import com.google.gson.annotations.SerializedName

data class MainDto(
	@SerializedName("feels_like")
	val feelsLike: Double?,
	@SerializedName("grnd_level")
	val grndLevel: Int?,
	@SerializedName("humidity")
	val humidity: Int?,
	@SerializedName("pressure")
	val pressure: Int?,
	@SerializedName("sea_level")
	val seaLevel: Int?,
	@SerializedName("temp")
	val temp: Double?,
	@SerializedName("temp_max")
	val tempMax: Double?,
	@SerializedName("temp_min")
	val tempMin: Double?
)