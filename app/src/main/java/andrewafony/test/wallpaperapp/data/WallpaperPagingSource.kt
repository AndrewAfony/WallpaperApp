package andrewafony.test.wallpaperapp.data

import andrewafony.test.wallpaperapp.data.remote.Ratio
import andrewafony.test.wallpaperapp.data.remote.WallpaperCloudDataSource
import andrewafony.test.wallpaperapp.data.remote.model.asWallpaper
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import androidx.paging.PagingSource
import androidx.paging.PagingState

class WallpaperPagingSource(
    private val cloudDataSource: WallpaperCloudDataSource,
    private val query: String?,
) : PagingSource<Int, Wallpaper>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Wallpaper> {

        val position = params.key ?: 1

        return try {

            val wallpapers =
                cloudDataSource.search(
                    query = query,
                    ratio = Ratio.PORTRAIT,
                    page = position
                )

            val nextKey = if (position < wallpapers.meta.last_page) {
                position.plus(1)
            } else
                null

            LoadResult.Page(
                data = wallpapers.data.map { it.asWallpaper() },
                prevKey = null,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Wallpaper>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}