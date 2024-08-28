package andrewafony.test.wallpaper_detail

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val wallpaperDetailModule = module {

    singleOf(::ImageSaver)
}