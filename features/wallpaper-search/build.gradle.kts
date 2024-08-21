plugins {
    alias(libs.plugins.wallpaper.feature.plugin)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "andrewafony.test.wallpaper_search"
}

dependencies {

    api(projects.core.common)
}