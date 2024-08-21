package andrewafony.test.domain.model

data class Wallpaper(
    val id: String,
    val url: String,
    val category: String,
    val source: String,
    val views: Int,
    val favorites: Int,
    val size: String,
    val resolution: String,
    val isSaved: Boolean
)