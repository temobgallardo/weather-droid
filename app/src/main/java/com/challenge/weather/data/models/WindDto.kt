package com.challenge.weather.data.models


import com.google.gson.annotations.SerializedName

data class WindDto(
	@SerializedName("deg")
	val deg: Int?,
	@SerializedName("gust")
	val gust: Double?,
	@SerializedName("speed")
	val speed: Double?
)