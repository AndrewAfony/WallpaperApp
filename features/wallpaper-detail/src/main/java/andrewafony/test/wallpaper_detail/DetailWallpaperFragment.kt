package andrewafony.test.wallpaper_detail

import andrewafony.test.common.BaseFragment
import andrewafony.test.wallpaper_detail.databinding.FragmentDetailWallpaperBinding
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import coil3.request.allowHardware
import coil3.request.crossfade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class DetailWallpaperFragment : BaseFragment<FragmentDetailWallpaperBinding>(
    showBottomNavigation = false
) {

    private val imageSaver: ImageSaver by inject()

//    private val viewModel by viewModels<SavedWallpapersViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailWallpaperBinding
        get() = FragmentDetailWallpaperBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

//        lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.currentWallpaper.collectLatest { wallpaper ->
//                    binding.fullWallpaper.load(wallpaper?.url) {
//                        placeholderMemoryCacheKey(wallpaper?.url)
//                        crossfade(true)
//                        allowHardware(false)
//                        listener(
//                            onError = { _, _ ->
//                                startPostponedEnterTransition()
//                            },
//                            onSuccess = { _, result ->
//                                androidx.palette.graphics.Palette.Builder(result.image.toBitmap()).generate { palette ->
//                                    palette?.getDominantColor(Color.WHITE)?.let {
//                                        binding.root.setBackgroundColor(it)
//                                        binding.buttonBack.setButtonColor(it)
//                                    }
//                                }
//                                startPostponedEnterTransition()
//                            }
//                        )
//                    }
//
//                    binding.buttonInfo.setOnClickListener {
//                        WallpaperInfoBottomSheetFragment.Companion.open(parentFragmentManager, wallpaper)
//                    }
//                }
//            }
//        }

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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
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