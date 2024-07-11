package andrewafony.test.wallpaperapp.data.remote.model

import andrewafony.test.wallpaperapp.core.Mapper
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import kotlinx.serialization.Serializable

@Serializable
data class WallpapersSearchResponse(
    val data: List<WallpaperInfo>,
    val meta: WallpaperMeta
): Mapper<List<Wallpaper>> {

    override fun map(): List<Wallpaper> {
        return data.map { it.asWallpaper() }
    }
}