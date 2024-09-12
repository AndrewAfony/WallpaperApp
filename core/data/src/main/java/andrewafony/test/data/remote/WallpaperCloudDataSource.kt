package andrewafony.test.data.remote

import andrewafony.test.data.remote.model.WallpaperSearchResponse
import andrewafony.test.data.remote.model.WallpapersSearchResponse

interface WallpaperCloudDataSource {

    suspend fun search(
        query: String?,
        ratio: Ratio?,
        page: Int,
    ): WallpapersSearchResponse

    suspend fun searchById(
        id: String
    ): WallpaperSearchResponse

    class Base (private val service: WallpapersApi) : WallpaperCloudDataSource {

        override suspend fun search(
            query: String?,
            ratio: Ratio?,
            page: Int,
        ): WallpapersSearchResponse {
            return service.search(query, ratio, page)
        }

        override suspend fun searchById(id: String): WallpaperSearchResponse {
            return service.searchById(id)
        }
    }
}