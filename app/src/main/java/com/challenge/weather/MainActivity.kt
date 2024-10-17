package com.challenge.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.challenge.weather.ui.theme.WeatherTheme
import com.challenge.weather.ui.weather.WeatherView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		enableEdgeToEdge()
		setContent {
			WeatherTheme {
				Surface {
					Scaffold {
						WeatherView(
							modifier = Modifier
								.fillMaxSize()
								.padding(it)
						)
					}
				}
			}
		}
	}
}
