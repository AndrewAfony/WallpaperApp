package andrewafony.test.wallpaperapp.data.repository

import andrewafony.test.wallpaperapp.data.WallpaperPagingSource
import andrewafony.test.wallpaperapp.data.remote.Ratio
import andrewafony.test.wallpaperapp.data.remote.WallpaperCloudDataSource
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import andrewafony.test.wallpaperapp.domain.repository.WallpaperRepository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BaseWallpaperRepository @Inject constructor(
    private val cloudDataSource: WallpaperCloudDataSource,
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

    companion object {

        private const val PAGE_SIZE = 24
    }
}