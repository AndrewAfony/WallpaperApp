package andrewafony.test.wallpaperapp

import andrewafony.test.common.NavigateToDetail
import andrewafony.test.wallpaperapp.navigation.MainNavigation
import andrewafony.test.wallpaperapp.navigation.MainNavigationImpl
import andrewafony.test.wallpaperapp.navigation.NavigateToDetailImpl
import andrewafony.test.wallpaperapp.navigation.Navigation
import andrewafony.test.wallpaperapp.navigation.NavigationImpl
import androidx.fragment.app.FragmentManager
import org.koin.dsl.module

val navigationModule = module {

    single<Int> { R.id.container }

    single<Navigation> { NavigationImpl(get()) }

    factory<MainNavigation> { MainNavigationImpl(get()) }

    factory<NavigateToDetail> { (manager: FragmentManager) -> NavigateToDetailImpl(get(), manager) }
}

