package andrewafony.test.wallpaperapp.domain.repository

import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import kotlinx.coroutines.flow.Flow


interface WallpaperRepository {

    fun searchWallpapers(query: String?): Flow<List<Wallpaper>>
}