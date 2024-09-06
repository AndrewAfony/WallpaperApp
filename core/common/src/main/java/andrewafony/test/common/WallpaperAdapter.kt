package andrewafony.test.common

import andrewafony.test.common.databinding.WallpaperItemBinding
import andrewafony.test.domain.model.Wallpaper
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.memory.MemoryCache
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import coil3.size.Dimension

class WallpaperAdapter(
    private val onClick: (Wallpaper) -> Unit,
    private val onToggleFavorite: (Wallpaper) -> Unit,
) : PagingDataAdapter<Wallpaper, WallpaperViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        return WallpaperViewHolder(
            WallpaperItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick,
            onToggleFavorite
        )
    }

    override fun onBindViewHolder(
        holder: WallpaperViewHolder,
        position: Int,
    ) {
        getItem(position)?.let { holder.bind(it) }
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
}

class WallpaperViewHolder(
    private val binding: WallpaperItemBinding,
    private val onClick: (Wallpaper) -> Unit,
    private val onToggleFavorite: (Wallpaper) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(wallpaper: Wallpaper) { // todo loader and error

        with(binding.wallpaper) {
            scaleType = ImageView.ScaleType.CENTER_CROP
            load(wallpaper.url) {
                error(ColorDrawable(Color.RED))
                size(Dimension.Pixels(250), Dimension.Undefined)
                crossfade(true)
            }
        }

        bindStar(wallpaper)

        binding.root.setOnClickListener { onClick(wallpaper) }
    }

    fun bindStar(wallpaper: Wallpaper) {
        val star = if (wallpaper.isSaved) R.drawable.ic_star_filled else R.drawable.ic_star

        with(binding.buttonLike) {
            setImageResource(star)
            setOnClickListener { onToggleFavorite(wallpaper) }
        }
    }
}
