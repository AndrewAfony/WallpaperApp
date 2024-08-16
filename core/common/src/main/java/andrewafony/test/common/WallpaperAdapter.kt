package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.wallpaperapp.R
import andrewafony.test.wallpaperapp.databinding.WallpaperItemBinding
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.ImageLoader
import coil3.load
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.placeholder
import coil3.result
import coil3.size.Dimension
import coil3.util.CoilUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.set

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
                memoryCacheKey(wallpaper.url)
                size(Dimension.Pixels(250), Dimension.Undefined)
                placeholder(R.drawable.placeholder)
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
