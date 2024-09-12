package andrewafony.test.data.local.entities

import andrewafony.test.domain.model.Wallpaper
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
    val resolution: String,
    val isSaved: Boolean,
)

fun Wallpaper.toEntity() = WallpaperEntity(
    id = id,
    url = url,
    category = category,
    source = source,
    views = views,
    favorites = favorites,
    size = size,
    resolution = resolution,
    isSaved = isSaved
)

fun WallpaperEntity.toWallpaper() = Wallpaper(
    id = id,
    url = url,
    category = category,
    source = source,
    views = views,
    favorites = favorites,
    size = size,
    resolution = resolution,
    uploader = null,
    tags = emptyList(),
    isSaved = isSaved
)