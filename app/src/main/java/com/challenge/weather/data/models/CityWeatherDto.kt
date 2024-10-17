package com.challenge.weather.data.models


import com.google.gson.annotations.SerializedName

data class CityWeatherDto(
	@SerializedName("base")
	val base: String?,
	@SerializedName("clouds")
	val clouds: CloudsDto?,
	@SerializedName("cod")
	val cod: Int?,
	@SerializedName("coord")
	val coord: CoordDto?,
	@SerializedName("dt")
	val dt: Int?,
	@SerializedName("id")
	val id: Int?,
	@SerializedName("main")
	val main: MainDto?,
	@SerializedName("name")
	val name: String?,
	@SerializedName("sys")
	val sys: SysDto?,
	@SerializedName("timezone")
	val timezone: Int?,
	@SerializedName("visibility")
	val visibility: Int?,
	@SerializedName("weather")
	val weather: List<WeatherDto?>?,
	@SerializedName("wind")
	val wind: WindDto?
)