plugins {
    alias(libs.plugins.wallpaper.feature.plugin)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "andrewafony.test.data"
}

dependencies {

    implementation(projects.core.common)
    implementation(projects.core.domain)

    // ==== Retrofit ====
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)

    // ==== Room ====
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}