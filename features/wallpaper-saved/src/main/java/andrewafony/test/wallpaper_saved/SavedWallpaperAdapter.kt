package andrewafony.test.wallpaper_saved

import andrewafony.test.common.DiffUtilCallback
import andrewafony.test.common.WallpaperPayloads
import andrewafony.test.common.WallpaperViewHolder
import andrewafony.test.common.databinding.WallpaperItemBinding
import andrewafony.test.domain.model.Wallpaper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

internal class SavedWallpaperAdapter(
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