package andrewafony.test.wallpaperapp.navigation

import andrewafony.test.wallpaperapp.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

interface Navigation {

    fun add(manager: FragmentManager, fragment: Class<out Fragment>)

    fun add(manager: FragmentManager, fragment: Class<out Fragment>, bundle: Bundle)

    fun replace(manager: FragmentManager, fragment: Class<out Fragment>, addToBackStack: Boolean = false)

    fun backPress(manager: FragmentManager)
}

class NavigationImpl(
    private val container: Int,
) : Navigation {

    override fun add(manager: FragmentManager, fragment: Class<out Fragment>) = manager.commit {
        setReorderingAllowed(true)
        setCustomAnimations(
            andrewafony.test.common.R.anim.enter_from_right,
            andrewafony.test.common.R.anim.exit_to_left,
            andrewafony.test.common.R.anim.enter_from_left,
            andrewafony.test.common.R.anim.exit_to_right
        )
        add(container, fragment, null)
        addToBackStack(fragment.simpleName)
    }

    override fun add(manager: FragmentManager, fragment: Class<out Fragment>, bundle: Bundle) =
        manager.commit {
            setReorderingAllowed(true)
            setCustomAnimations(
                andrewafony.test.common.R.anim.enter_from_right,
                andrewafony.test.common.R.anim.exit_to_left,
                andrewafony.test.common.R.anim.enter_from_left,
                andrewafony.test.common.R.anim.exit_to_right
            )
            add(container, fragment, bundle)
            addToBackStack(fragment.simpleName)
        }

    override fun replace(
        manager: FragmentManager,
        fragment: Class<out Fragment>,
        addToBackStack: Boolean,
    ) = manager.commit {
//        setReorderingAllowed(true)
        replace(container, fragment, null)
        if (addToBackStack)
            addToBackStack(null)
    }

    override fun backPress(manager: FragmentManager) {
        manager.popBackStack()
    }
}