package andrewafony.test.wallpaperapp

import andrewafony.test.wallpaper_detail.DetailWallpaperFragment
import andrewafony.test.wallpaper_saved.SavedWallpapersFragment
import andrewafony.test.wallpaper_search.MainFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import org.koin.dsl.module
import java.lang.Class

val navigationModule = module {

    single<Int> { R.id.container }

    single<Class<out Fragment>> { DetailWallpaperFragment::class.java }

    single<MainNavigation> { (manager: FragmentManager) -> MainNavigationImpl(manager) }
}

interface MainNavigation {

    fun init()

    fun navigate(itemId: Int): Boolean
}

class MainNavigationImpl(
    private val manager: FragmentManager,
    private val container: Int = R.id.container,
) : MainNavigation {

    private val mainFragment = MainFragment()
    private val savedFragment = SavedWallpapersFragment()

    private var currentFragment: Fragment = mainFragment

    override fun init() {
        manager.commit {
//            add(container, mainFragment)
//            add(container, savedFragment)
//            hide(savedFragment)
            replace(container, mainFragment)
        }
    }

    override fun navigate(itemId: Int): Boolean {
        return when (itemId) {
            R.id.main -> {
//                manager.commit {
//                    hide(currentFragment)
//                    show(mainFragment)
//                }
//                currentFragment = mainFragment
                manager.commit{
                    replace(container, mainFragment)
                    addToBackStack(null)
                }
                 true
            }
            R.id.saved -> {
//                manager.commit {
//                    hide(currentFragment)
//                    show(savedFragment)
//                }
//                currentFragment = savedFragment
                manager.commit{
                    replace(container, savedFragment)
                    addToBackStack(null)
                }
                true
            }
            else -> false
        }
        return true
    }
}