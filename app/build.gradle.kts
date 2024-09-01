plugins {
    alias(libs.plugins.wallpaper.android.plugin)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "andrewafony.test.wallpaperapp"

    defaultConfig {
        applicationId = "andrewafony.test.wallpaperapp"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {

    implementation(projects.features.wallpaperSearch)
    implementation(projects.features.wallpaperSaved)
    implementation(projects.features.wallpaperDetail)
    implementation(projects.core.data)
//    implementation(projects.core.common)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.koin.test.junit4)

//    implementation("com.github.terrakok:cicerone:7.1")
}