package andrewafony.test.wallpaperapp.presentation

import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import andrewafony.test.wallpaperapp.domain.repository.WallpaperRepository
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WallpaperRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    private val _currentWallpaper = MutableStateFlow<Wallpaper?>(null)
    val currentWallpaper: StateFlow<Wallpaper?> = _currentWallpaper

    val wallpapers = searchQuery
        .debounce { query -> if (query.isNotEmpty()) 500 else 0 }
        .flatMapLatest { query ->
            repository.wallpapersPaging(query)
        }
        .cachedIn(viewModelScope)

    val savedWallpapers = repository.savedWallpapers()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onSearch(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    fun openWallpaper(wallpaper: Wallpaper) {
        _currentWallpaper.value = wallpaper
    }

    fun saveWallpaper(wallpaper: Wallpaper) {
        viewModelScope.launch {
            repository.saveWallpaper(wallpaper)
        }
    }

    fun removeWallpaper(wallpaper: Wallpaper) {
        viewModelScope.launch {
            repository.removeWallpaper(wallpaper)
        }
    }

    companion object {

        private const val SEARCH_QUERY = "search_query"
    }
}