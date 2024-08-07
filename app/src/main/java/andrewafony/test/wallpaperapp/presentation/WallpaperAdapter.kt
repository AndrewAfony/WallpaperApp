package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.wallpaperapp.databinding.WallpaperItemBinding
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import andrewafony.test.wallpaperapp.R

class WallpaperAdapter(
    private val onClick: (Wallpaper) -> Unit,
    private val onSaveClick: (Wallpaper) -> Unit,
) : PagingDataAdapter<Wallpaper, WallpaperViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        return WallpaperViewHolder(
            WallpaperItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick,
            onSaveClick
        )
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

object DiffUtilCallback : DiffUtil.ItemCallback<Wallpaper>() {

    override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
        return oldItem == newItem
    }
}


class WallpaperViewHolder(
    private val binding: WallpaperItemBinding,
    private val onClick: (Wallpaper) -> Unit,
    private val onSaveClick: (Wallpaper) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(wallpaper: Wallpaper) { // todo loader and error

        with(binding.wallpaper) {
            scaleType = ImageView.ScaleType.CENTER_CROP
            load(wallpaper.url)
        }

        with(binding.buttonLike) { // todo update star when clicked
            val star = if (wallpaper.isSaved) R.drawable.ic_star_filled else R.drawable.ic_star
            setImageResource(star)
            setOnClickListener { onSaveClick(wallpaper) }
        }

        binding.root.setOnClickListener { onClick(wallpaper) }
    }
}