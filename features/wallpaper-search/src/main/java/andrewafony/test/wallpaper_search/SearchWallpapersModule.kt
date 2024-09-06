package andrewafony.test.wallpaper_search

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchWallpapersModule = module {

    viewModelOf(::SearchViewModel)

    single<SearchScreenNavigation> { params ->
        SearchScreenNavigationImpl(
            params.get(),
            get(),
            get()
        )
    }
}

class SearchScreenNavigationImpl(
    private val manager: FragmentManager,
    private val container: Int,
    private val fragment: Class<out Fragment>,
) : SearchScreenNavigation {

    override fun navigateToDetail(url: String) = manager.commit {
        setReorderingAllowed(true)
        setCustomAnimations(
            andrewafony.test.common.R.anim.enter_from_right,
            andrewafony.test.common.R.anim.exit_to_left,
            andrewafony.test.common.R.anim.enter_from_left,
            andrewafony.test.common.R.anim.exit_to_right
        )
        add(container, fragment, bundleOf("url" to url))
        addToBackStack(fragment.simpleName)
    }
}

interface SearchScreenNavigation {
    fun navigateToDetail(url: String)
}