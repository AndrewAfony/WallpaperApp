package andrewafony.test.data.local

import andrewafony.test.data.local.entities.WallpaperEntity
import kotlinx.coroutines.flow.Flow

interface WallpaperLocalDataSource {

    fun wallpapers(): Flow<List<WallpaperEntity>>

    suspend fun getWallpaperById(id: String): WallpaperEntity

    suspend fun saveWallpaper(wallpaper: WallpaperEntity)

    suspend fun deleteWallpaper(wallpaper: WallpaperEntity)

    suspend fun exists(id: String) : Boolean

    class Base (private val dao: WallpaperDao) : WallpaperLocalDataSource {

        override fun wallpapers(): Flow<List<WallpaperEntity>> = dao.wallpapers()

        override suspend fun getWallpaperById(id: String): WallpaperEntity =
            dao.getWallpaperById(id)

        override suspend fun saveWallpaper(wallpaper: WallpaperEntity) =
            dao.saveWallpaper(wallpaper)

        override suspend fun deleteWallpaper(wallpaper: WallpaperEntity) =
            dao.deleteWallpaper(wallpaper)

        override suspend fun exists(id: String): Boolean = dao.exists(id)
    }
}