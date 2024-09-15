package andrewafony.test.wallpaperapp.navigation

import andrewafony.test.common.NavigateToDetail
import andrewafony.test.wallpaper_detail.DetailWallpaperFragment
import andrewafony.test.wallpaperapp.R
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

class NavigateToDetailImpl(
    private val navigation: Navigation,
    private val manager: FragmentManager,
) : NavigateToDetail {

    override fun navigateToDetail(url: String) {
        navigation.add(manager, DetailWallpaperFragment::class.java, bundleOf("url" to url))
    }
}