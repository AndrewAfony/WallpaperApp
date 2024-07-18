package andrewafony.test.wallpaperapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andrewafony.test.wallpaperapp.R
import andrewafony.test.wallpaperapp.core.BaseFragment
import andrewafony.test.wallpaperapp.databinding.FragmentDetailWallpaperBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailWallpaperFragment : BaseFragment<FragmentDetailWallpaperBinding>() {

    private val viewModel by activityViewModels<MainViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailWallpaperBinding
        get() = FragmentDetailWallpaperBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentWallpaper.collectLatest { uri ->
                    Glide
                        .with(binding.root)
                        .load(uri)
                        .into(binding.fullWallpaper)
                }
            }
        }
    }
}