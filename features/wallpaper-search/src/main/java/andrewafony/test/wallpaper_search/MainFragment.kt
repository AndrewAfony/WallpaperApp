package andrewafony.test.wallpaper_search

import andrewafony.test.common.BaseFragment
import andrewafony.test.common.WallpaperAdapter
import andrewafony.test.wallpaper_search.databinding.FragmentMainBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainFragment : BaseFragment<FragmentMainBinding>(true) {

    private val searchViewModel by viewModel<SearchViewModel>()

    private val navigation: SearchScreenNavigation by inject { parametersOf(parentFragmentManager) }

    private lateinit var adapter: WallpaperAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar.editText?.doOnTextChanged { query, _, _, _ ->
            searchViewModel.onSearch(query.toString())
        }

        adapter = WallpaperAdapter(
            onClick = { wallpaper ->
                navigation.navigateToDetail(wallpaper.url)
            },
            onToggleFavorite = searchViewModel::toggleFavorite
        )

        binding.rvWallpapers.adapter = adapter

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                binding.progressBar.visibility =
                    if (loadState.refresh is androidx.paging.LoadState.Loading) View.VISIBLE else View.GONE
                if (loadState.refresh is androidx.paging.LoadState.Error) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text =
                        (loadState.refresh as androidx.paging.LoadState.Error).error.message
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
            searchViewModel.wallpapers.collectLatest(adapter::submitData)
        }
    }
}