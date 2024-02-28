plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.data"

    compileSdk = 34
}

dependencies {
    implementation(project(":domain"))
}