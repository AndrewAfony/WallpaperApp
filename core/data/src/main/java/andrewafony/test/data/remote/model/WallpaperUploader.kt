package andrewafony.test.data.remote.model

import andrewafony.test.domain.model.Uploader
import kotlinx.serialization.Serializable

@Serializable
data class WallpaperUploader(
    val username: String,
    val group: String,
    val avatar: Map<String, String>,
)

fun WallpaperUploader.asUploader() = Uploader(
    username = username,
    group = group,
    avatar = avatar["128px"] ?: avatar["32px"] ?: ""
)