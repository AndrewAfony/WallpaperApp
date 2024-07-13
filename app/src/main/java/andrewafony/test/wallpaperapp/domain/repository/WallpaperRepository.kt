package andrewafony.test.wallpaperapp.domain.repository

import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow


interface WallpaperRepository {

    fun searchWallpapers(query: String?): Flow<List<Wallpaper>>

    fun wallpapersPaging(query: String): Flow<PagingData<Wallpaper>>
}