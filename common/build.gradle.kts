plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.common"

    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    api("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    api("androidx.core:core-ktx:1.12.0")
    api("androidx.activity:activity-compose:1.8.2")
    api(platform("androidx.compose:compose-bom:2023.08.00"))
    api("androidx.compose.ui:ui")
    api("androidx.compose.ui:ui-graphics")
    api("androidx.compose.ui:ui-tooling-preview")
    api("androidx.compose.material3:material3")

    api("androidx.navigation:navigation-compose:2.7.7")

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")

    api("junit:junit:4.13.2")

    api("app.cash.turbine:turbine:1.0.0")

    debugApi("androidx.compose.ui:ui-tooling")
    debugApi("androidx.compose.ui:ui-test-manifest")
}