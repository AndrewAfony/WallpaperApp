package andrewafony.test.data.repository

import andrewafony.test.data.local.WallpaperLocalDataSource
import andrewafony.test.data.local.entities.toEntity
import andrewafony.test.data.local.entities.toWallpaper
import andrewafony.test.data.remote.WallpaperCloudDataSource
import andrewafony.test.domain.model.Wallpaper
import andrewafony.test.domain.repository.WallpaperRepository
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WallpaperRepositoryImpl(
    private val cloudDataSource: WallpaperCloudDataSource,
    private val localDataSource: WallpaperLocalDataSource
) : WallpaperRepository {

    override fun wallpapersPaging(query: String): Flow<PagingData<Wallpaper>> {
        return androidx.paging.Pager(
            config = androidx.paging.PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                andrewafony.test.data.WallpaperPagingSource(
                    cloudDataSource,
                    query
                )
            }
        ).flow
    }

    override fun savedWallpapers(): Flow<List<Wallpaper>> =
        localDataSource.wallpapers().map { it.map { it.toWallpaper() } }


    override suspend fun getWallpaperById(id: String): Wallpaper =
        kotlinx.coroutines.withContext(Dispatchers.IO) {
            localDataSource.getWallpaperById(id).toWallpaper()
        }

    override suspend fun toggleFavorite(wallpaper: Wallpaper) =
        kotlinx.coroutines.withContext(Dispatchers.IO) {

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

