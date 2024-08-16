package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.common.BaseFragment
import andrewafony.test.wallpaper_saved.DetailWallpaperFragment
import andrewafony.test.wallpaperapp.R
import andrewafony.test.wallpaperapp.databinding.FragmentMainBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(true) {

    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var adapter: WallpaperAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar.editText?.doOnTextChanged { query, _, _, _ ->
            viewModel.onSearch(query.toString())
        }

        adapter = WallpaperAdapter(
            onClick = { wallpaper ->
                viewModel.openWallpaper(wallpaper)
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    add(R.id.container, DetailWallpaperFragment())
                    addToBackStack(null)
                }
            },
            onToggleFavorite = viewModel::toggleFavorite
        )

        binding.rvWallpapers.adapter = adapter

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                binding.progressBar.visibility =
                    if (loadState.refresh is LoadState.Loading) View.VISIBLE else View.GONE
                if (loadState.refresh is LoadState.Error) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = (loadState.refresh as LoadState.Error).error.message
                } else
                    binding.errorText.visibility = View.GONE
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.searchBar.hasFocus()) {
                        binding.searchBar.clearFocus()
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            })

        lifecycleScope.launch {
            viewModel.wallpapers.collectLatest(adapter::submitData)
        }
    }
}