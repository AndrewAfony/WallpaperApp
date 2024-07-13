package andrewafony.test.wallpaperapp.data

import andrewafony.test.wallpaperapp.data.remote.Ratio
import andrewafony.test.wallpaperapp.data.remote.WallpaperCloudDataSource
import andrewafony.test.wallpaperapp.data.remote.WallpapersApi
import andrewafony.test.wallpaperapp.data.remote.model.asWallpaper
import andrewafony.test.wallpaperapp.domain.model.Wallpaper
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.flow

class WallpaperPagingSource(
    private val cloudDataSource: WallpaperCloudDataSource,
    private val query: String?,
) : PagingSource<Int, Wallpaper>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Wallpaper> {

        return try {
            val nextPageNumber = params.key ?: 1

            val wallpapers =
                cloudDataSource.search(
                    query = query,
                    ratio = Ratio.PORTRAIT,
                    page = nextPageNumber)

            LoadResult.Page(
                data = wallpapers.data.map { it.asWallpaper() },
                prevKey = null,
                nextKey = nextPageNumber.plus(1)
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