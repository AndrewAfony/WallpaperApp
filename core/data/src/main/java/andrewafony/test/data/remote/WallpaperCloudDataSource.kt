package andrewafony.test.wallpaperapp.data.remote

import andrewafony.test.wallpaperapp.data.remote.model.WallpapersSearchResponse
import android.util.Log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WallpaperCloudDataSource {

    suspend fun search(
        query: String?,
        ratio: Ratio?,
        page: Int,
    ): WallpapersSearchResponse

    class Base @Inject constructor(private val service: WallpapersApi) : WallpaperCloudDataSource {

        override suspend fun search(
            query: String?,
            ratio: Ratio?,
            page: Int,
        ): WallpapersSearchResponse {
            return service.search(query, ratio, page)
        }
    }
}