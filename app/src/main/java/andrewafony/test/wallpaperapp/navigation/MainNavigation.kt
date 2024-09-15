package andrewafony.test.wallpaperapp.navigation

import andrewafony.test.wallpaper_saved.SavedWallpapersFragment
import andrewafony.test.wallpaper_search.MainFragment
import andrewafony.test.wallpaperapp.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

interface MainNavigation {

    fun init(manager: FragmentManager)

    fun setupBottomNavigation(manager: FragmentManager, item: Int): Boolean

    fun backPress(manager: FragmentManager, onFinish: () -> Unit)
}

class MainNavigationImpl(
    private val container: Int,
) : MainNavigation {

    private val mainFragment = MainFragment()
    private val savedFragment = SavedWallpapersFragment()

    private var currentFragment: Fragment = mainFragment

    override fun init(manager: FragmentManager) {
//        manager.commit {
//            add(container, fragments[1]!!)
//            add(container, fragments[2]!!)
//            hide(fragments[2]!!)
//        }
        manager.commit {
            replace(container, mainFragment, null)
            addToBackStack(null)
        }
    }

    override fun setupBottomNavigation(manager: FragmentManager, item: Int): Boolean {
//        bottomNavigation.setOnItemSelectedListener { item ->
        return when (item) {
            R.id.main -> {
//                manager.commit {
//                    hide(currentFragment)
//                    show(fragments[1]!!)
//                }
//                currentFragment = fragments[1]!!
                manager.commit {
                    replace(container, mainFragment, null)
                    addToBackStack(null)
                }
                true
            }

            R.id.saved -> {
//                manager.commit {
//                    hide(currentFragment)
//                    show(fragments[2]!!)
//                }
//                currentFragment = fragments[2]!!
                manager.commit {
                    replace(container, savedFragment, null)
                    addToBackStack(null)
                }
                true
            }

            else -> false
        }
    }

    override fun backPress(
        manager: FragmentManager,
        onFinish: () -> Unit,
    ) {
//        when (currentFragment) {
//            mainFragment -> {
//                onFinish()
//            }
//
//            else -> {
//                manager.commit {
//                    hide(currentFragment)
//                    show(mainFragment)
//                }
//                currentFragment = mainFragment
//            }
//        }
    }
}