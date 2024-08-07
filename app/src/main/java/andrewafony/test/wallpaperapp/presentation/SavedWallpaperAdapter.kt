package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.wallpaperapp.databinding.WallpaperItemBinding
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class SavedWallpaperAdapter(
    private val onClick: (Wallpaper) -> Unit,
    private val onToggleFavorite: (Wallpaper) -> Unit
) : RecyclerView.Adapter<WallpaperViewHolder>() {

    private val wallpapers = mutableListOf<Wallpaper>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WallpaperViewHolder {
        return WallpaperViewHolder(
            WallpaperItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false),
            onClick = onClick,
            onToggleFavorite = onToggleFavorite
        )
    }

    override fun onBindViewHolder(
        holder: WallpaperViewHolder,
        position: Int,
    ) {
        holder.bind(wallpapers[position])
    }

    override fun getItemCount(): Int = wallpapers.size

    fun map(newList: List<Wallpaper>) {
        val diff = InnerDiffUtilCallback(wallpapers, newList)
        val res = DiffUtil.calculateDiff(diff)
        wallpapers.clear()
        wallpapers.addAll(newList)
        res.dispatchUpdatesTo(this)
    }
}

// todo remove duplicate diffutil from wallpaper adapter
private class InnerDiffUtilCallback(
    private val oldList: List<Wallpaper>,
    private val newList: List<Wallpaper>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}