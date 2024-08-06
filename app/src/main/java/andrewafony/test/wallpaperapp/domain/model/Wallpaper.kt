package andrewafony.test.wallpaperapp.domain.model

import andrewafony.test.wallpaperapp.data.local.entities.WallpaperEntity

data class Wallpaper(
    val id: String,
    val url: String,
    val category: String,
    val source: String,
    val views: Int,
    val favorites: Int,
    val size: String,
    val resolution: String
)

fun Wallpaper.asEntity() = WallpaperEntity(
    id = id,
    url = url,
    category = category,
    source = source,
    views = views,
    favorites = favorites,
    size = size,
    resolution = resolution
)
