plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.hilt.android)
	kotlin("kapt")
}

android {
	namespace = "com.challenge.weather"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.challenge.weather"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
	buildFeatures {
		compose = true
	}
}

dependencies {
	implementation(libs.hilt.android)
	implementation(libs.hilt.navigation.compose)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.splashscreen)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	implementation(libs.androidx.datastore)
	implementation(libs.retrofit.android)
	implementation(libs.glide.android)
	implementation(libs.gson.converter)
	implementation(libs.androidx.accompanist)
	implementation(libs.android.gms)
	implementation(libs.androidx.junit.ktx)
	implementation(libs.androidx.ui.text.google.fonts)
	testImplementation(libs.junit)
//	testImplementation(libs.junit.vintage)
	testImplementation(libs.mockito)
	testImplementation(libs.mockito.kotlin)
	testImplementation(libs.androidx.core.test)
	testImplementation(libs.kotlin.coroutines.test)
//	testImplementation(libs.junit5.jupiter.api)
//	testImplementation(libs.junit5.jupiter.engine)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
	kapt(libs.hilt.android.compiler)
}

// Allow references to generated code
kapt {
	correctErrorTypes = true
}