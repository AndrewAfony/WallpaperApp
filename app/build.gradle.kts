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

//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.palette.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    implementation(libs.retrofit)
//    implementation(libs.kotlinx.serialization.json)
//    implementation(libs.retrofit2.kotlinx.serialization.converter)
//    implementation(libs.okhttp)

    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.fragment.ktx)

//    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.testing)

//    implementation(libs.androidx.room.runtime)
//    implementation(libs.androidx.room.ktx)
//    ksp(libs.androidx.room.compiler)

    implementation("com.github.terrakok:cicerone:7.1")
}