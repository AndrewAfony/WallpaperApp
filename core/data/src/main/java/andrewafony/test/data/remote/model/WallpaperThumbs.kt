package andrewafony.test.data.remote.model

import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class WallpaperThumbs(
    val large: String,
    val original: String,
    val small: String
)