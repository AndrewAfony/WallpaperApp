package andrewafony.test.data.remote

import andrewafony.test.data.remote.model.WallpapersSearchResponse

interface WallpaperCloudDataSource {

    suspend fun search(
        query: String?,
        ratio: Ratio?,
        page: Int,
    ): WallpapersSearchResponse

    class Base (private val service: WallpapersApi) : WallpaperCloudDataSource {

        override suspend fun search(
            query: String?,
            ratio: Ratio?,
            page: Int,
        ): WallpapersSearchResponse {
            return service.search(query, ratio, page)
        }
    }
}