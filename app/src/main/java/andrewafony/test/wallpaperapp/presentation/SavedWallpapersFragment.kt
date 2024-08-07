package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.wallpaperapp.core.BaseFragment
import andrewafony.test.wallpaperapp.databinding.FragmentSavedWallpapersBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class SavedWallpapersFragment : BaseFragment<FragmentSavedWallpapersBinding>(true) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSavedWallpapersBinding
        get() = FragmentSavedWallpapersBinding::inflate

    private lateinit var adapter: SavedWallpaperAdapter

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SavedWallpaperAdapter(
            onClick = {},
            onRemove = viewModel::removeWallpaper
        )

        binding.rvSavedWallpapers.adapter = adapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.savedWallpapers.collect { adapter.map(it) }
            }
        }
    }
}