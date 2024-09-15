package andrewafony.test.wallpaperapp

import andrewafony.test.common.BottomNavigationController
import andrewafony.test.wallpaper_saved.SavedWallpapersFragment
import andrewafony.test.wallpaper_search.MainFragment
import andrewafony.test.wallpaperapp.databinding.ActivityMainBinding
import andrewafony.test.wallpaperapp.navigation.MainNavigation
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), BottomNavigationController {

    private lateinit var binding: ActivityMainBinding

    private val navigation: MainNavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigation.init(supportFragmentManager)
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            navigation.setupBottomNavigation(supportFragmentManager, item.itemId)
        }

//        binding.bottomNavigation.setOnItemReselectedListener { item ->
//            when(item.itemId) {
//                R.id.mainFragment -> {
//                    Log.d("MyHelper", "reselect")
//                }
//            }
//        }


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigation.backPress(supportFragmentManager) { finish() }
            }
        })
    }

    override fun show() {
        binding.bottomNavigation.visibility = View.VISIBLE

    }

    override fun hide() {
        binding.bottomNavigation.visibility = View.GONE
    }
}