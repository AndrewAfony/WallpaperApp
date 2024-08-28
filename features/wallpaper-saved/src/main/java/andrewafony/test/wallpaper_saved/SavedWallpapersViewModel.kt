package andrewafony.test.wallpaper_saved

import andrewafony.test.domain.model.Wallpaper
import andrewafony.test.domain.repository.WallpaperRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class SavedWallpapersViewModel(
    private val repository: WallpaperRepository
): ViewModel() {

    val savedWallpapers = repository.savedWallpapers()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun toggleFavorite(wallpaper: Wallpaper) {
        viewModelScope.launch {
            repository.toggleFavorite(wallpaper)
        }
    }
}