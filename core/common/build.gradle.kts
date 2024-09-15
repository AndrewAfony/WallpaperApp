plugins {
    alias(libs.plugins.wallpaper.feature.plugin)
}

android {
    namespace = "andrewafony.test.common"
}

dependencies {

    api(projects.core.domain)

    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)
    api(libs.androidx.activity)
    api(libs.androidx.constraintlayout)

    api(libs.androidx.palette.ktx)

    // ==== Coil ====
    api(libs.coil)
    api(libs.coil.network.okhttp)

    // ==== Fragments KTX ====
    api(libs.androidx.fragment.ktx)

    // ==== Paging ====
    api(libs.androidx.paging.runtime.ktx)

    // ==== Koin ====
    api(libs.koin.android)

    // ==== Tests ====
    debugImplementation (libs.leakcanary.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}