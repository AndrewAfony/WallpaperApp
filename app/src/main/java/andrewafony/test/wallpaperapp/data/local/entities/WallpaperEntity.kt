package andrewafony.test.wallpaperapp.data.local.entities

import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WallpaperEntity(
    @PrimaryKey val id: String,
    val url: String,
    val category: String,
    val source: String,
    val views: Int,
    val favorites: Int,
    val size: String,
    val resolution: String
)

fun WallpaperEntity.asWallpaper() = Wallpaper(
    id = id,
    url = url,
    category = category,
    source = source,
    views = views,
    favorites = favorites,
    size = size,
    resolution = resolution
)