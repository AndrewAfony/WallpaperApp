package andrewafony.test.wallpaper_detail

import andrewafony.test.domain.model.Wallpaper
import andrewafony.test.wallpaper_detail.databinding.WallpaperInfoBottomSheetDialogBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal class WallpaperInfoBottomSheetFragment(
    private val wallpaper: Wallpaper?
): BottomSheetDialogFragment() {

    private var _binding: WallpaperInfoBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WallpaperInfoBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            wallpaper?.let {
                resolution.text = it.resolution
                size.text = "${it.size} Mb"
                favorities.text = "${it.favorites}"
                views.text = "${it.views}"
                category.text = it.category
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun open(
            fragmentManager: FragmentManager,
            wallpaper: Wallpaper?
        ) =
            WallpaperInfoBottomSheetFragment(wallpaper).show(fragmentManager, "wallpaper_info")
    }
}