package andrewafony.test.data.remote.model

import andrewafony.test.domain.model.Tag
import kotlinx.serialization.Serializable

@Serializable
data class WallpaperTag(
    val id: Long,
    val name: String,
    val alias: String,
    val category_id: Long,
    val category: String,
    val purity: String,
    val created_at: String
)

fun WallpaperTag.asTag() = Tag(
    id = id,
    name = name,
    alias = alias,
    category_id = category_id,
    category = category,
    purity = purity,
    created_at = created_at
)