package andrewafony.test.data.remote

import andrewafony.test.data.remote.model.WallpaperSearchResponse
import andrewafony.test.data.remote.model.WallpapersSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WallpapersApi {

    @GET("search")
    suspend fun search(
        @Query("q") query: String? = null,
        @Query("ratios") ratio: Ratio? = Ratio.PORTRAIT,
        @Query("page") page: Int = 1
    ): WallpapersSearchResponse

    @GET("w/{id}")
    suspend fun searchById(
        @Path("id") id: String
    ): WallpaperSearchResponse
}

enum class Ratio(val ratio: String) {
    PORTRAIT("9x16")
}