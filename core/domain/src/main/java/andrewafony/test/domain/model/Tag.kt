package andrewafony.test.domain.model

data class Tag(
    val id: Long,
    val name: String,
    val alias: String,
    val category_id: Long,
    val category: String,
    val purity: String,
    val created_at: String
)
