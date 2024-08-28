package andrewafony.test.data.di

import andrewafony.test.data.local.WallpaperDatabase
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
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