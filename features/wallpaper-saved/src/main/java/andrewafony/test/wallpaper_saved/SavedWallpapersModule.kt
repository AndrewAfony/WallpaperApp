package andrewafony.test.wallpaper_saved

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val savedWallpapersModule = module {

    viewModelOf(::SavedWallpapersViewModel)
}