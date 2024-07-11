package andrewafony.test.wallpaperapp.data.remote.model

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class WallpaperMeta(
    val current_page: Int,
    val last_page: Int,
    val per_page: Int,
    @Serializable(WallpaperMetaQuerySerializer::class)
    val query: MetaQuery?,
    val seed: String? = null,
    val total: Int
)

interface MetaQuery

@JvmInline
@Serializable
value class StringQuery(val query: String) : MetaQuery

@Serializable
data class TagQuery(
    val id: Int,
    val tag: String
) : MetaQuery

object WallpaperMetaQuerySerializer : JsonContentPolymorphicSerializer<MetaQuery>(MetaQuery::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<MetaQuery> = when(element) {
        is JsonPrimitive -> StringQuery.serializer()
        else -> TagQuery.serializer()
    }
}