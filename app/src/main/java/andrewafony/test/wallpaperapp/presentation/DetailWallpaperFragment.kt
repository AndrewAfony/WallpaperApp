package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.wallpaperapp.core.BaseFragment
import andrewafony.test.wallpaperapp.databinding.FragmentDetailWallpaperBinding
import andrewafony.test.wallpaperapp.domain.ImageSaver
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.palette.graphics.Palette
import coil3.load
import coil3.request.allowHardware
import coil3.toBitmap
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
                    binding.fullWallpaper.load(wallpaper?.url) {
                        allowHardware(false)
                        listener(
                            onError = { _, _ ->
                                startPostponedEnterTransition()
                            },
                            onSuccess = { _, result ->
                                Palette.Builder(result.image.toBitmap()).generate { palette ->
                                    palette?.getDominantColor(Color.WHITE)?.let {
                                        binding.root.setBackgroundColor(it)
                                        binding.buttonBack.setButtonColor(it)
                                    }
                                }
                                startPostponedEnterTransition()
                            }
                        )
                    }

                    binding.buttonInfo.setOnClickListener {
                        WallpaperInfoBottomSheetFragment.open(parentFragmentManager, wallpaper)
                    }
                }
            }
        }

        binding.buttonDownload.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val bitmap = binding.fullWallpaper.drawable.toBitmap()
                ImageSaver.saveImageToGallery(
                    requireContext(),
                    bitmap,
                    "wallpaper_${System.currentTimeMillis()}"
                )
            }
        }

        binding.buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}

fun ImageView.setButtonColor(backgroundColor: Int) {
    val luminance = calculateLuminance(backgroundColor)
    val buttonColor = if (luminance < 0.5) Color.WHITE else Color.BLACK

    setColorFilter(buttonColor)
}

fun calculateLuminance(color: Int): Double {
    val red = Color.red(color) / 255.0
    val green = Color.green(color) / 255.0
    val blue = Color.blue(color) / 255.0

    return 0.299 * red + 0.587 * green + 0.114 * blue
}