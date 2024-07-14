package andrewafony.test.wallpaperapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andrewafony.test.wallpaperapp.R
import andrewafony.test.wallpaperapp.databinding.FragmentMainBinding
import android.util.Log
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: WallpaperAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

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

        adapter = WallpaperAdapter()
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
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            viewModel.wallpapers.collectLatest(adapter::submitData)
        }
    }
}