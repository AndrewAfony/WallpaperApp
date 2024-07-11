package andrewafony.test.wallpaperapp.data.repository

import andrewafony.test.wallpaperapp.data.remote.Ratio
import andrewafony.test.wallpaperapp.data.remote.WallpaperCloudDataSource
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import andrewafony.test.wallpaperapp.domain.repository.WallpaperRepository
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class BaseWallpaperRepository @Inject constructor(
    private val cloudDataSource: WallpaperCloudDataSource,
) : WallpaperRepository {

    override fun searchWallpapers(query: String?): Flow<List<Wallpaper>> {
        return flow {
            emit(
                cloudDataSource.search(
                    query = query,
                    ratio = Ratio.PORTRAIT,
                    page = 1
                )
            )
        }
            .flowOn(Dispatchers.IO) // todo DI dispatcher
            .map { it.map() }
    }
}