package andrewafony.test.wallpaperapp.data.remote.model

import andrewafony.test.wallpaperapp.core.Mapper
import andrewafony.test.wallpaperapp.data.local.entities.WallpaperEntity
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import kotlinx.serialization.Serializable

@Serializable
data class WallpaperInfo(
    val category: String,
    val colors: List<String>,
    val created_at: String,
    val dimension_x: Int,
    val dimension_y: Int,
    val favorites: Int,
    val file_size: Int,
    val file_type: String,
    val id: String,
    val path: String,
    val purity: String,
    val ratio: String,
    val resolution: String,
    val short_url: String,
    val source: String,
    val thumbs: WallpaperThumbs,
    val url: String,
    val views: Int
)

fun WallpaperInfo.asWallpaper() = Wallpaper(
    id = id,
    url = path,
    category = category,
    source = source,
    views = views,
    favorites = favorites,
    size = "%.1f".format(file_size/1048576.0),
    resolution = resolution
)

fun WallpaperInfo.asWallpaperEntity() = WallpaperEntity(
    id = id.hashCode()
)