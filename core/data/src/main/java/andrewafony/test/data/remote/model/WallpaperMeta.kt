package andrewafony.test.data.remote.model

@kotlinx.serialization.Serializable
data class WallpaperMeta(
    val current_page: Int,
    val last_page: Int,
    val per_page: Int,
    @kotlinx.serialization.Serializable(WallpaperMetaQuerySerializer::class)
    val query: MetaQuery?,
    val seed: String? = null,
    val total: Int
)

interface MetaQuery

@JvmInline
@kotlinx.serialization.Serializable
value class StringQuery(val query: String) : MetaQuery

@kotlinx.serialization.Serializable
data class TagQuery(
    val id: Int,
    val tag: String
) : MetaQuery

object WallpaperMetaQuerySerializer : kotlinx.serialization.json.JsonContentPolymorphicSerializer<MetaQuery>(MetaQuery::class) {
    override fun selectDeserializer(element: kotlinx.serialization.json.JsonElement): kotlinx.serialization.DeserializationStrategy<MetaQuery> = when(element) {
        is kotlinx.serialization.json.JsonPrimitive -> StringQuery.serializer()
        else -> TagQuery.serializer()
    }
}