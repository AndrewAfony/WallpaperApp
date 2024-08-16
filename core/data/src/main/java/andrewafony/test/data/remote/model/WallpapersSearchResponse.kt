package andrewafony.test.wallpaperapp.data.remote.model

import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import kotlinx.serialization.Serializable

@Serializable
data class WallpapersSearchResponse(
    val data: List<WallpaperInfo>,
    val meta: WallpaperMeta
): andrewafony.test.common.Mapper<List<Wallpaper>> {

    override fun map(): List<Wallpaper> {
        return data.map { it.asWallpaper() }
    }
}