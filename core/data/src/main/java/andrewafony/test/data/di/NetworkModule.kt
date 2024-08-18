package andrewafony.test.data.di

import andrewafony.test.data.remote.WallpapersApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

internal val networkModule = module {

    single {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(Json.Default.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://wallhaven.cc/api/v1/")
            .build()

        retrofit.create(WallpapersApi::class.java)

    }

}