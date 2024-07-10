package andrewafony.test.wallpaperapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val current_page: Int,
    val last_page: Int,
    val per_page: Int,
    val query: MetaQuery?,
    val seed: String? = null,
    val total: Int
)

interface MetaQuery

@Serializable
data class StringQuery(val query: String) : MetaQuery

@Serializable
data class TagQuery(
    val id: Int,
    val tag: String
) : MetaQuery