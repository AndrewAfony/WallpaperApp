package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.wallpaperapp.databinding.WallpaperItemBinding
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SavedWallpaperAdapter(
    private val onClick: (Wallpaper) -> Unit,
    private val onToggleFavorite: (Wallpaper) -> Unit,
) : ListAdapter<Wallpaper, WallpaperViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WallpaperViewHolder {
        return WallpaperViewHolder(
            WallpaperItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick = onClick,
            onToggleFavorite = onToggleFavorite
        )
    }

    override fun onBindViewHolder(
        holder: WallpaperViewHolder,
        position: Int,
        payloads: List<Any?>,
    ) {
        when (payloads.firstOrNull()) {
            WallpaperPayloads.Favorite -> getItem(position)?.let { holder.bindStar(it) }
            else -> onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(
        holder: WallpaperViewHolder,
        position: Int,
    ) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun map(newList: List<Wallpaper>) {
        submitList(newList)
    }
}