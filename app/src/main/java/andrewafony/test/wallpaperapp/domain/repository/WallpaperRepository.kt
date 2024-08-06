package andrewafony.test.wallpaperapp.domain.repository

import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow


interface WallpaperRepository {

    fun wallpapersPaging(query: String): Flow<PagingData<Wallpaper>>

    fun savedWallpapers() : Flow<List<Wallpaper>>

    suspend fun getWallpaperById(id: String) : Wallpaper

    suspend fun saveWallpaper(wallpaper: Wallpaper)

    suspend fun removeWallpaper(wallpaper: Wallpaper)
}