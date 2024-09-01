plugins {
    alias(libs.plugins.wallpaper.feature.plugin)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "andrewafony.test.wallpaper_saved"
}

dependencies {

    implementation(projects.core.common)

//    implementation(libs.androidx.palette)
}