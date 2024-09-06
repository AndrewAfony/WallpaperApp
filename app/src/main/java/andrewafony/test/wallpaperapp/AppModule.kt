package andrewafony.test.wallpaperapp

import andrewafony.test.data.di.dataModule
import andrewafony.test.wallpaper_detail.wallpaperDetailModule
import andrewafony.test.wallpaper_saved.savedWallpapersModule
import andrewafony.test.wallpaper_search.searchWallpapersModule
import org.koin.dsl.module

val appModule = module {
    includes(
        dataModule,
        searchWallpapersModule,
        savedWallpapersModule,
        wallpaperDetailModule,
        navigationModule,
    )
}