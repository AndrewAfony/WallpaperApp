package andrewafony.test.wallpaper_detail

import andrewafony.test.domain.use_cases.SearchWallpaperByIdUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val wallpaperDetailModule = module {

    single { SearchWallpaperByIdUseCase(get()) }

    singleOf(::ImageSaver)

    viewModelOf(::DetailWallpaperViewModel)
}