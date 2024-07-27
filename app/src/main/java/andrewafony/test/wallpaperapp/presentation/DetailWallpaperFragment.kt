package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.wallpaperapp.core.BaseFragment
import andrewafony.test.wallpaperapp.databinding.FragmentDetailWallpaperBinding
import andrewafony.test.wallpaperapp.domain.ImageSaver
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailWallpaperFragment : BaseFragment<FragmentDetailWallpaperBinding>() {

    private val viewModel by activityViewModels<MainViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailWallpaperBinding
        get() = FragmentDetailWallpaperBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentWallpaper.collectLatest { wallpaper ->
                    Glide
                        .with(binding.root)
                        .load(wallpaper?.url)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>,
                                isFirstResource: Boolean,
                            ): Boolean {
                                startPostponedEnterTransition()
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any,
                                target: Target<Drawable>?,
                                dataSource: DataSource,
                                isFirstResource: Boolean,
                            ): Boolean {
                                startPostponedEnterTransition()
                                return false
                            }
                        })
                        .into(binding.fullWallpaper)

                    binding.buttonInfo.setOnClickListener {
                        WallpaperInfoBottomSheetFragment.open(parentFragmentManager, wallpaper)
                    }
                }
            }
        }

        binding.buttonDownload.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val bitmap = binding.fullWallpaper.drawable.toBitmap()
                ImageSaver.saveImageToGallery(requireContext(), bitmap, "wallpaper_${System.currentTimeMillis()}")
            }
        }

        binding.buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}