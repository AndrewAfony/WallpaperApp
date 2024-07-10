package andrewafony.test.wallpaperapp.data.remote

import andrewafony.test.wallpaperapp.data.model.WallpapersResponse

interface CloudDataSource {

    suspend fun search(
        query: String?,
        ratio: String?
    ) : WallpapersResponse

    class Base(private val service: WallpapersApi) : CloudDataSource {

        override suspend fun search(query: String?, ratio: String?): WallpapersResponse {
            return service.search(query, ratio)
        }
    }
}