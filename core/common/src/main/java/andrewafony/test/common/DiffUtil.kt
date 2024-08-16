package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import androidx.recyclerview.widget.DiffUtil

object DiffUtilCallback : DiffUtil.ItemCallback<Wallpaper>() {

    override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItem: Wallpaper,
        newItem: Wallpaper,
    ): Any? {
        return when {
            oldItem.isSaved != newItem.isSaved -> WallpaperPayloads.Favorite
            else -> super.getChangePayload(oldItem, newItem)
        }
    }
}

sealed interface WallpaperPayloads {

    data object Favorite : WallpaperPayloads
}