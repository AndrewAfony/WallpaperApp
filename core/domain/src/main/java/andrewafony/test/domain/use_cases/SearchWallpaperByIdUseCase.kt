package andrewafony.test.domain.use_cases

import andrewafony.test.domain.model.Wallpaper
import andrewafony.test.domain.repository.WallpaperRepository

class SearchWallpaperByIdUseCase(
    private val repository: WallpaperRepository,
) {

    suspend operator fun invoke(id: String): Wallpaper = repository.searchWallpaperById(id)
}