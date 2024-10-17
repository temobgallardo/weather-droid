package com.challenge.weather.data.models


import com.google.gson.annotations.SerializedName

data class CloudsDto(
	@SerializedName("all")
	val all: Int?
)