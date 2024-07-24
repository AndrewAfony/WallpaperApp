package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.wallpaperapp.R
import andrewafony.test.wallpaperapp.core.BaseFragment
import andrewafony.test.wallpaperapp.databinding.FragmentMainBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var adapter: WallpaperAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearch(newText ?: "")
                return false
            }
        })

        adapter = WallpaperAdapter { wallpaper ->
            viewModel.openWallpaper(wallpaper)

            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add<DetailWallpaperFragment>(R.id.container)
                addToBackStack(null)
            }
        }

        binding.rvWallpapers.adapter = adapter

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                binding.progressBar.visibility =
                    if (loadState.refresh is LoadState.Loading) View.VISIBLE else View.GONE
                if (loadState.refresh is LoadState.Error) {
                    Log.d("MyHelper", "error: ${(loadState.refresh as LoadState.Error).error}")
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = (loadState.refresh as LoadState.Error).error.message
                } else
                    binding.errorText.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            viewModel.wallpapers.collectLatest(adapter::submitData)
        }
    }
}