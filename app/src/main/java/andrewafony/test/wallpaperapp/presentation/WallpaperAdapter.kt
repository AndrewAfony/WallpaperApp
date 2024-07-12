package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.wallpaperapp.databinding.WallpaperItemBinding
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WallpaperAdapter: RecyclerView.Adapter<WallpaperViewHolder>() {

    private val wallpapers: MutableList<Wallpaper> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        return WallpaperViewHolder(
            WallpaperItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        holder.bind(wallpapers[position])
    }

    override fun getItemCount(): Int = wallpapers.size

    fun map(newWallpapers: List<Wallpaper>) { // todo diffutil
        wallpapers.clear()
        wallpapers.addAll(newWallpapers)
        notifyDataSetChanged()
    }
}


class WallpaperViewHolder(
    private val binding: WallpaperItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(wallpaper: Wallpaper) { // todo loader and error
        Glide
            .with(binding.root)
            .load(wallpaper.url)
            .centerCrop()
            .into(binding.wallpaper)
    }
}