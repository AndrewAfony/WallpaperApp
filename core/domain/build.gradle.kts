plugins {
    alias(libs.plugins.wallpaper.feature.plugin)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "andrewafony.test.domain"
}

dependencies {

    implementation(libs.androidx.paging.runtime.ktx)
    testImplementation(libs.junit)
}