package andrewafony.test.wallpaper_search

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchWallpapersModule = module {

    viewModelOf(::SearchViewModel)
}