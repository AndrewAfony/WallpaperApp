package andrewafony.test.data.remote

interface WallpaperCloudDataSource {

    suspend fun search(
        query: String?,
        ratio: Ratio?,
        page: Int,
    ): andrewafony.test.data.remote.model.WallpapersSearchResponse

    class Base (private val service: WallpapersApi) : WallpaperCloudDataSource {

        override suspend fun search(
            query: String?,
            ratio: Ratio?,
            page: Int,
        ): andrewafony.test.data.remote.model.WallpapersSearchResponse {
            return service.search(query, ratio, page)
        }
    }
}