package andrewafony.test.wallpaper_detail

import andrewafony.test.wallpaper_detail.databinding.WallpaperInfoBottomSheetDialogBinding
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class WallpaperInfoBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModel<DetailWallpaperViewModel>(ownerProducer = { requireParentFragment() })

    private var _binding: WallpaperInfoBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = WallpaperInfoBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.wallpaperInfo.collectLatest { wallpaper ->
                    wallpaper?.let {
                        with(binding) {
                            uploader.text = wallpaper.uploader?.username
                            uploaderImage.load(wallpaper.uploader?.avatar) {
                                transformations(CircleCropTransformation())
                                placeholder(ColorDrawable(Color.LTGRAY))
                            }
                            tags.text = wallpaper.tags.map { "#${it.name}" }.take(10)
                                .filter { it.isNotBlank() }.joinToString(" ")
                            resolution.text = wallpaper.resolution
                            size.text = "${wallpaper.size} Mb"
                            favorities.text = "${wallpaper.favorites}"
                            views.text = "${wallpaper.views}"
                            category.text = wallpaper.category
                        }
                    }
                }
            }
        }

//        with(binding) {
//            wallpaper?.let {
//                resolution.text = it.resolution
//                size.text = "${it.size} Mb"
//                favorities.text = "${it.favorites}"
//                views.text = "${it.views}"
//                category.text = it.category
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun open(
            fragmentManager: FragmentManager,
        ) = WallpaperInfoBottomSheetFragment().show(fragmentManager, "wallpaper_info")
    }
}