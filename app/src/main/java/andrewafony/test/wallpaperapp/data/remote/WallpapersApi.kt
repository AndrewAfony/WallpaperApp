package andrewafony.test.wallpaperapp.data.remote

import andrewafony.test.wallpaperapp.data.remote.model.WallpapersSearchResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpapersApi {

    @GET("search")
    suspend fun search(
        @Query("q") query: String? = null,
        @Query("ratios") ratio: Ratio? = Ratio.PORTRAIT,
        @Query("page") page: Int = 1
    ): WallpapersSearchResponse
}

enum class Ratio(val ratio: String) {
    PORTRAIT("9x16")
}

object ServiceApi {


}