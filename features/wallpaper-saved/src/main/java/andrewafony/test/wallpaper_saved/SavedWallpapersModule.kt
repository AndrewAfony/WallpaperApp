package andrewafony.test.wallpaper_saved

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val savedWallpapersModule = module {

    viewModelOf(::SavedWallpapersViewModel)

    single<SavedWallpapersNavigation> { params ->
        SavedWallpapersNavigationImpl(
            params.get(),
            get(),
            get()
        )
    }
}

class SavedWallpapersNavigationImpl(
    private val manager: FragmentManager,
    private val container: Int,
    private val fragment: Class<out Fragment>,
) : SavedWallpapersNavigation {

    override fun navigateToDetail(url: String) = manager.commit {
        setReorderingAllowed(true)
        add(container, fragment, bundleOf("url" to url))
        addToBackStack(fragment.simpleName)
    }
}

interface SavedWallpapersNavigation {
    fun navigateToDetail(url: String)
}