package andrewafony.test.data.remote

interface WallpapersApi {

    @retrofit2.http.GET("search")
    suspend fun search(
        @retrofit2.http.Query("q") query: String? = null,
        @retrofit2.http.Query("ratios") ratio: Ratio? = Ratio.PORTRAIT,
        @retrofit2.http.Query("page") page: Int = 1
    ): andrewafony.test.data.remote.model.WallpapersSearchResponse
}

enum class Ratio(val ratio: String) {
    PORTRAIT("9x16")
}