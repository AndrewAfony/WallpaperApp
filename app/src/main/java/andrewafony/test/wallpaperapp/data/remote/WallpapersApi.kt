package andrewafony.test.wallpaperapp.data.remote

import andrewafony.test.wallpaperapp.data.model.Wallpapers
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperApi {

    @GET("search")
    suspend fun search(
        @Query("q") query: String? = null,
        @Query("ratios") ratio: String = "9x16",
    ): Wallpapers
}

object Service {

    fun create() {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("")
            .build()
            .create(WallpaperApi::class.java)
    }
}