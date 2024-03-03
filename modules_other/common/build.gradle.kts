plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.common"

    compileSdk = 34
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")

    api("junit:junit:4.13.2")

    api("app.cash.turbine:turbine:1.0.0")
}