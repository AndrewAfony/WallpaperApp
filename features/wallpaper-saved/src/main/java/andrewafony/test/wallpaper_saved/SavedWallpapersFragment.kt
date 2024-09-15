package andrewafony.test.wallpaper_saved

import andrewafony.test.common.BaseFragment
import andrewafony.test.common.NavigateToDetail
import andrewafony.test.wallpaper_saved.databinding.FragmentSavedWallpapersBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SavedWallpapersFragment : BaseFragment<FragmentSavedWallpapersBinding>(true) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSavedWallpapersBinding
        get() = FragmentSavedWallpapersBinding::inflate

    private lateinit var adapter: SavedWallpaperAdapter

    private val navigation: NavigateToDetail by inject { parametersOf(parentFragmentManager) }

    private val savedViewModel by viewModel<SavedWallpapersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SavedWallpaperAdapter(
            onClick = { wallpaper ->
                navigation.navigateToDetail(wallpaper.url)
            },
            onToggleFavorite = savedViewModel::toggleFavorite
        )

        binding.rvSavedWallpapers.adapter = adapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                savedViewModel.savedWallpapers.collectLatest { adapter.map(it) }
            }
        }
    }
}