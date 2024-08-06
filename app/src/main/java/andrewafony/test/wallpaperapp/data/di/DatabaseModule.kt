package andrewafony.test.wallpaperapp.data.di

import andrewafony.test.wallpaperapp.data.local.WallpaperDao
import andrewafony.test.wallpaperapp.data.local.WallpaperDatabase
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): WallpaperDatabase = Room.databaseBuilder(
        context,
        WallpaperDatabase::class.java,
        "wallpaper_db"
    ).build()

    @Provides
    fun providesWallpaperDao(database: WallpaperDatabase) = database.wallpaperDao()
}