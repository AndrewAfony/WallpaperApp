package andrewafony.test.wallpaper_detail

import andrewafony.test.domain.model.Wallpaper
import andrewafony.test.domain.use_cases.SearchWallpaperByIdUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailWallpaperViewModel(
    private val searchWallpaperById: SearchWallpaperByIdUseCase,
) : ViewModel() {

    private val _wallpaperInfo = MutableStateFlow<Wallpaper?>(null)
    val wallpaperInfo: StateFlow<Wallpaper?> = _wallpaperInfo

    fun loadWallpaperInfo(id: String) {
        viewModelScope.launch { // TODO handle error and loading
            val result = searchWallpaperById(id)
            withContext(Dispatchers.Main) {
                _wallpaperInfo.value = result
            }
        }
    }

}