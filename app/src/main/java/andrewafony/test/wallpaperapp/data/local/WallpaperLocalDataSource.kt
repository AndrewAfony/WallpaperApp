package andrewafony.test.wallpaperapp.data.local

import andrewafony.test.wallpaperapp.data.local.entities.WallpaperEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WallpaperLocalDataSource {

    fun wallpapers(): Flow<List<WallpaperEntity>>

    suspend fun getWallpaperById(id: String): WallpaperEntity

    suspend fun saveWallpaper(wallpaper: WallpaperEntity)

    suspend fun removeWallpaper(wallpaper: WallpaperEntity)

    class Base @Inject constructor (private val dao: WallpaperDao) : WallpaperLocalDataSource {

        override fun wallpapers(): Flow<List<WallpaperEntity>> = dao.wallpapers()

        override suspend fun getWallpaperById(id: String): WallpaperEntity =
            dao.getWallpaperById(id)

        override suspend fun saveWallpaper(wallpaper: WallpaperEntity) =
            dao.saveWallpaper(wallpaper)

        override suspend fun removeWallpaper(wallpaper: WallpaperEntity) =
            dao.removeWallpaper(wallpaper)
    }
}