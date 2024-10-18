package com.challenge.weather.ui.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.challenge.weather.R
import com.challenge.weather.viewModels.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherView(modifier: Modifier = Modifier, viewModel: WeatherViewModel = hiltViewModel()) {
	val textHint = "Type city to search here"
	val textFieldDescription = "Search City Weather"
	val supportingText = "You may refine search by using: city, country code, state code"
	val context = LocalContext.current
	val contextFocus = LocalFocusManager.current
	// Reactive string to search city weather
	val textToSearch = remember { viewModel.searchTerm }
	val weatherResult = remember { viewModel.weather }
	val focusRequester = remember { FocusRequester() }

	Column(modifier = modifier) {
		Column(
			modifier = Modifier
				.fillMaxWidth(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			OutlinedTextField(
				value = textToSearch.value,
				onValueChange = { viewModel.updateSearchTerm(it) },
				singleLine = true,
				placeholder = { Text(textHint) },
				label = { Text(textFieldDescription) },
				supportingText = { Text(supportingText) },
				keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
				keyboardActions = KeyboardActions(
					onDone = {
						viewModel.getWeather()
						contextFocus.clearFocus()
					}
				),
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(focusRequester),
				trailingIcon = {
					if (textToSearch.value.isNotEmpty()) {
						IconButton(onClick = { viewModel.updateSearchTerm("") }) {
							Icon(
								imageVector = Icons.Outlined.Clear,
								contentDescription = null
							)
						}
					}
				}
			)
		}

		Box(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f)
		) {
			// Show if result
			weatherResult.value?.let {
				OutlinedCard(
					modifier = Modifier
						.fillMaxWidth(0.9f)
						.align(Alignment.TopCenter)
						.padding(vertical = 12.dp),
					colors = CardDefaults.cardColors(
						containerColor = MaterialTheme.colorScheme.tertiary,
						contentColor = MaterialTheme.colorScheme.onTertiary
					),
					elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
				) {
					Row(
						modifier = Modifier
							.fillMaxWidth()
							.padding(8.dp)
					) {
						Column(modifier = Modifier.weight(1f)) {
							Text(
								text = it.city,
								style = MaterialTheme.typography.titleLarge,
								modifier = Modifier.padding(horizontal = 4.dp)
							)
							Text(
								text = it.description,
								style = MaterialTheme.typography.titleMedium,
								modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
							)
							Text(
								text = "${it.temperature}° Fahrenheit",
								style = MaterialTheme.typography.bodyLarge,
								modifier = Modifier.padding(horizontal = 4.dp)
							)
						}

						Box(contentAlignment = Alignment.Center) {
							// Glide cached automatically the image to save resources
							GlideImage(
								imageModel = { context.getString(R.string.icon_url, it.iconCode) },
								modifier = Modifier.size(80.dp)
							)
						}
					}
				}
			}
		}
	}

	// Based on the permission from user
	val locationPermissionState = rememberMultiplePermissionsState(
		listOf(
			android.Manifest.permission.ACCESS_FINE_LOCATION,
			android.Manifest.permission.ACCESS_COARSE_LOCATION
		)
	)

	val lifecycleOwner = LocalLifecycleOwner.current
	DisposableEffect(lifecycleOwner) {
		val observer = LifecycleEventObserver { _, event ->
			if (event == Lifecycle.Event.ON_START && !locationPermissionState.allPermissionsGranted) {
				// Request user location permissions
				locationPermissionState.launchMultiplePermissionRequest()
			}
		}

		lifecycleOwner.lifecycle.addObserver(observer)

		onDispose {
			lifecycleOwner.lifecycle.removeObserver(observer)
		}
	}

	LaunchedEffect(locationPermissionState) {
		if (locationPermissionState.allPermissionsGranted) {
			viewModel.getWeatherByUsersLocation()
		}
	}

	// Fire and forget section
	LaunchedEffect(true) {
		// give the focus back to the app
		focusRequester.requestFocus()
		viewModel.getLastCityAndWeather()
	}
}