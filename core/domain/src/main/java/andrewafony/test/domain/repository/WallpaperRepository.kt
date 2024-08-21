package andrewafony.test.domain.repository

import andrewafony.test.domain.model.Wallpaper
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {

    fun wallpapersPaging(query: String): Flow<PagingData<Wallpaper>>

    fun savedWallpapers() : Flow<List<Wallpaper>>

    suspend fun getWallpaperById(id: String) : Wallpaper

    suspend fun toggleFavorite(wallpaper: Wallpaper)
}