package andrewafony.test.wallpaperapp.di

import andrewafony.test.wallpaperapp.data.remote.WallpaperCloudDataSource
import andrewafony.test.wallpaperapp.data.repository.BaseWallpaperRepository
import andrewafony.test.wallpaperapp.domain.repository.WallpaperRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindsCloudDataSource(
        cloudDataSource: WallpaperCloudDataSource.Base
    ) : WallpaperCloudDataSource

    @Binds
    fun bindsWallpaperRepository(
        repository: BaseWallpaperRepository
    ) : WallpaperRepository
}