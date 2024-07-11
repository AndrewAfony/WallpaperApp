package andrewafony.test.wallpaperapp.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class WallpaperThumbs(
    val large: String,
    val original: String,
    val small: String
)