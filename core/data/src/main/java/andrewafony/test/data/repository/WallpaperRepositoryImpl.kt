package andrewafony.test.data.repository

import andrewafony.test.data.WallpaperPagingSource
import andrewafony.test.data.local.WallpaperLocalDataSource
import andrewafony.test.data.local.entities.toEntity
import andrewafony.test.data.local.entities.toWallpaper
import andrewafony.test.data.remote.WallpaperCloudDataSource
import andrewafony.test.data.remote.model.asWallpaper
import andrewafony.test.domain.model.Wallpaper
import andrewafony.test.domain.repository.WallpaperRepository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class WallpaperRepositoryImpl(
    private val cloudDataSource: WallpaperCloudDataSource,
    private val localDataSource: WallpaperLocalDataSource,
) : WallpaperRepository {

    override fun wallpapersPaging(query: String): Flow<PagingData<Wallpaper>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                WallpaperPagingSource(
                    cloudDataSource,
                    query
                )
            }
        ).flow
    }

    override fun savedWallpapers(): Flow<List<Wallpaper>> =
        localDataSource.wallpapers().map { it.map { it.toWallpaper() } }

    override suspend fun searchWallpaperById(id: String): Wallpaper = withContext(Dispatchers.IO) {
        cloudDataSource.searchById(id).map()
    }

    override suspend fun getWallpaperById(id: String): Wallpaper =
        withContext(Dispatchers.IO) {
            localDataSource.getWallpaperById(id).toWallpaper()
        }

    override suspend fun toggleFavorite(wallpaper: Wallpaper) =
        withContext(Dispatchers.IO) {

            val exist = localDataSource.exists(wallpaper.id)

            if (!exist) {
                localDataSource.saveWallpaper((wallpaper.toEntity().copy(isSaved = true)))
            } else
                localDataSource.deleteWallpaper(wallpaper.toEntity())
        }

    companion object {

        private const val PAGE_SIZE = 24
    }
}

