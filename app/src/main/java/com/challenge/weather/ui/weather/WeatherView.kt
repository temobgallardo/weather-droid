package com.challenge.weather.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.challenge.weather.R
import com.challenge.weather.viewModels.WeatherViewModel
import com.skydoves.landscapist.glide.GlideImage

// At the top level of your kotlin file:
//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Composable
fun WeatherView(modifier: Modifier = Modifier, viewModel: WeatherViewModel = hiltViewModel()) {
	val textHint = "Type the city to search here"
	val textFieldDescription = "Search city"
	val context = LocalContext.current
	val contextFocus = LocalFocusManager.current
	// Reactive string to search city weather
	val textToSearch = remember { viewModel.searchTerm }
	val weatherResult = remember { viewModel.weather }
	val focusRequester = remember { FocusRequester() }
	Column(modifier) {
		Column(
			modifier = Modifier
				.fillMaxWidth(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			TextField(
				value = textToSearch.value,
				onValueChange = { viewModel.updateSearchTerm(it) },
				singleLine = true,
				placeholder = { Text(textHint) },
				label = { Text(textFieldDescription) },
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
			Button(onClick = { viewModel.getWeather() }) {
				Text("Search")
			}
		}



		Box(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f)
				.background(Color.Blue)
		) {
			// Show if result
			weatherResult.value?.let {
				Card(
					modifier = Modifier
						.fillMaxWidth(0.9f)
						.align(Alignment.Center)
				) {
					Text(
						text = it.city
					)
					Text(
						text = it.description
					)
					Text(
						text = it.temperature.toString()
					)
					GlideImage(
						imageModel = { context.getString(R.string.icon_url, it.iconCode) },
						modifier = Modifier.size(70.dp)
					)
				}
			}
		}
	}

	LaunchedEffect(true) {
		focusRequester.requestFocus()
	}
}