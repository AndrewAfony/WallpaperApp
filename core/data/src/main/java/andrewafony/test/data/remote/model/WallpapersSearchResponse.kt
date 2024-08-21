package andrewafony.test.data.remote.model

import andrewafony.test.common.MapperTo
import andrewafony.test.domain.model.Wallpaper
import kotlinx.serialization.Serializable

@Serializable
data class WallpapersSearchResponse(
    val data: List<WallpaperInfo>,
    val meta: WallpaperMeta
): MapperTo<List<Wallpaper>> {

    override fun map(): List<Wallpaper> {
        return data.map { it.asWallpaper() }
    }
}