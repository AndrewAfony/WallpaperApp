package andrewafony.test.data.di

import andrewafony.test.data.local.WallpaperLocalDataSource
import andrewafony.test.data.remote.WallpaperCloudDataSource
import andrewafony.test.data.repository.WallpaperRepositoryImpl
import andrewafony.test.domain.repository.WallpaperRepository
import org.koin.dsl.module

val dataModule = module {

    includes(databaseModule, networkModule)

    single<WallpaperCloudDataSource> { WallpaperCloudDataSource.Base(get()) }
    single<WallpaperLocalDataSource> { WallpaperLocalDataSource.Base(get()) }

    single<WallpaperRepository> { WallpaperRepositoryImpl(get(), get()) }
}