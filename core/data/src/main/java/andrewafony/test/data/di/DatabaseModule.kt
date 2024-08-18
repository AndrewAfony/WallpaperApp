package andrewafony.test.data.di

import andrewafony.test.data.local.WallpaperDatabase
import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            WallpaperDatabase::class.java,
            "wallpaper_db"
        ).build()
    }

    single {
        get<WallpaperDatabase>().wallpaperDao()
    }
}