package andrewafony.test.wallpaper_search

import andrewafony.test.common.BaseFragment
import andrewafony.test.common.NavigateToDetail
import andrewafony.test.common.WallpaperAdapter
import andrewafony.test.wallpaper_search.databinding.FragmentMainBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainFragment : BaseFragment<FragmentMainBinding>(true) {

    private val searchViewModel by viewModel<SearchViewModel>()

    private val navigation : NavigateToDetail by inject { parametersOf(parentFragmentManager) }

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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.wallpapers.collectLatest(adapter::submitData)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch { // TODO bug: при открытии приложения при быстром нажатии на второй фрагмент в bottom navigation
            adapter.loadStateFlow.collect { loadState ->
                binding.progressBar.visibility = if (loadState.refresh is LoadState.Loading) View.VISIBLE else View.GONE
                if (loadState.refresh is LoadState.Error) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = (loadState.refresh as LoadState.Error).error.message
                } else
                    binding.errorText.visibility = View.GONE
            }
        }
    }
}