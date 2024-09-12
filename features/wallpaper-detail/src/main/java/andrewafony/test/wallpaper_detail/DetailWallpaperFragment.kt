package andrewafony.test.wallpaper_detail

import andrewafony.test.common.BaseFragment
import andrewafony.test.wallpaper_detail.databinding.FragmentDetailWallpaperBinding
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import coil3.load
import coil3.request.allowHardware
import coil3.request.crossfade
import coil3.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailWallpaperFragment :
    BaseFragment<FragmentDetailWallpaperBinding>(showBottomNavigation = false) {

    private val imageSaver: ImageSaver by inject()

    private val viewModel by viewModel<DetailWallpaperViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailWallpaperBinding
        get() = FragmentDetailWallpaperBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        val url = arguments?.getString("url")
        val id = url?.substringAfterLast("-")?.substringBefore(".") ?: ""

        binding.fullWallpaper.scaleType = ImageView.ScaleType.CENTER_CROP
        binding.fullWallpaper.load(url) {
            allowHardware(false)
            listener(
                onError = { _, _ ->
                    startPostponedEnterTransition()
                },
                onSuccess = { _, result ->
                    Palette.Builder(result.image.toBitmap())
                        .generate { palette ->
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
            viewModel.loadWallpaperInfo(id)
            WallpaperInfoBottomSheetFragment.open(childFragmentManager)
        }

        binding.buttonDownload.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val bitmap = binding.fullWallpaper.drawable.toBitmap()
                imageSaver.saveImageToGallery(
                    requireContext(),
                    bitmap,
                    "wallpaper_${System.currentTimeMillis()}"
                )
            }
        }

        binding.buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.popBackStack()
                }
            })
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