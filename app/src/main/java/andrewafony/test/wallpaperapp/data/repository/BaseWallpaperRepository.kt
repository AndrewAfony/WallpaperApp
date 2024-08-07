package andrewafony.test.wallpaperapp.data.repository

import andrewafony.test.wallpaperapp.data.WallpaperPagingSource
import andrewafony.test.wallpaperapp.data.local.WallpaperLocalDataSource
import andrewafony.test.wallpaperapp.data.local.entities.WallpaperEntity
import andrewafony.test.wallpaperapp.data.local.entities.asWallpaper
import andrewafony.test.wallpaperapp.data.remote.WallpaperCloudDataSource
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import andrewafony.test.wallpaperapp.domain.model.asEntity
import andrewafony.test.wallpaperapp.domain.repository.WallpaperRepository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BaseWallpaperRepository @Inject constructor(
    private val cloudDataSource: WallpaperCloudDataSource,
    private val localDataSource: WallpaperLocalDataSource,
) : WallpaperRepository {

    override fun wallpapersPaging(query: String): Flow<PagingData<Wallpaper>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { WallpaperPagingSource(cloudDataSource, query) }
        ).flow
    }

    override fun savedWallpapers(): Flow<List<Wallpaper>> =
        localDataSource.wallpapers().map { it.map(WallpaperEntity::asWallpaper) }


    override suspend fun getWallpaperById(id: String): Wallpaper = withContext(Dispatchers.IO) {
        localDataSource.getWallpaperById(id).asWallpaper()
    }

    override suspend fun saveWallpaper(wallpaper: Wallpaper) = withContext(Dispatchers.IO) {
        localDataSource.saveWallpaper(wallpaper.asEntity().copy(isSaved = true))
    }

    override suspend fun removeWallpaper(wallpaper: Wallpaper) = withContext(Dispatchers.IO) {
        localDataSource.removeWallpaper(wallpaper.asEntity().copy(isSaved = false))
    }

    companion object {

        private const val PAGE_SIZE = 24
    }
}

