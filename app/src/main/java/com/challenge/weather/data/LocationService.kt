package com.challenge.weather.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.challenge.weather.domain.ILocationService
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.Locale
import javax.inject.Inject

class LocationService @Inject constructor(@ApplicationContext private val context: Context) :
	ILocationService {
	private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

	@SuppressLint("MissingPermission")
	override suspend fun getCurrentCityName() = callbackFlow<String> {
		val callback = OnSuccessListener<Location> {
			it?.let {
				val geocoder = Geocoder(context, Locale.ENGLISH)
				// For Android 34, we need 4 argument method, for previous min version this is fine
				val addressList = geocoder.getFromLocation(it.latitude, it.longitude, 1)
				val firstAddress = addressList?.firstOrNull()
				firstAddress?.let { address ->
					trySend(address.locality)
				}
			}
		}

		fusedLocationClient.lastLocation.addOnSuccessListener(callback)

		awaitClose { }
	}

}