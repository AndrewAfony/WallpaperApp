package andrewafony.test.data.remote.model

import andrewafony.test.common.MapperTo
import andrewafony.test.domain.model.Wallpaper
import kotlinx.serialization.Serializable

@Serializable
data class WallpaperSearchResponse(
    val data: WallpaperInfo,
) : MapperTo<Wallpaper> {

    override fun map(): Wallpaper = data.asWallpaper()
}
