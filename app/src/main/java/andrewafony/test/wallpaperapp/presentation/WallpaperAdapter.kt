package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.wallpaperapp.databinding.WallpaperItemBinding
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class WallpaperAdapter(
    private val onClick : (String) -> Unit
): PagingDataAdapter<Wallpaper, WallpaperViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        return WallpaperViewHolder(
            WallpaperItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick
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
    private val onClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(wallpaper: Wallpaper) { // todo loader and error
        Glide
            .with(binding.root)
            .load(wallpaper.url)
            .centerCrop()
            .into(binding.wallpaper)

        binding.root.setOnClickListener { onClick(wallpaper.url) }
    }
}