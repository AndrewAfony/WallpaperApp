package andrewafony.test.wallpaper_search

import andrewafony.test.domain.model.Wallpaper
import andrewafony.test.domain.repository.WallpaperRepository
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val repository: WallpaperRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    private val _currentWallpaper = MutableStateFlow<Wallpaper?>(null)
    val currentWallpaper: StateFlow<Wallpaper?> = _currentWallpaper

    val wallpapers = searchQuery
        .debounce { query -> if (query.isNotEmpty()) 500 else 0 }
        .flatMapLatest { query ->
            repository.wallpapersPaging(query)
        }
        .cachedIn(viewModelScope)
        .combine(repository.savedWallpapers()) { wallpapers, saved ->
            wallpapers.map { wallpaper ->
                wallpaper.copy(isSaved = saved.any { it.id == wallpaper.id })
            }
        }

    fun onSearch(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    fun openWallpaper(wallpaper: Wallpaper) {
        _currentWallpaper.value = wallpaper
    }

    fun toggleFavorite(wallpaper: Wallpaper) {
        viewModelScope.launch {
            repository.toggleFavorite(wallpaper)
        }
    }

    companion object {
        private const val SEARCH_QUERY = "search_query"
    }
}