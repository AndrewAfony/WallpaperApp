package andrewafony.test.wallpaperapp

import andrewafony.test.common.BottomNavigationController
import andrewafony.test.wallpaper_saved.SavedWallpapersFragment
import andrewafony.test.wallpaper_search.MainFragment
import andrewafony.test.wallpaperapp.databinding.ActivityMainBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity(), BottomNavigationController {

    private lateinit var binding: ActivityMainBinding

    private val mainFragment = MainFragment()
    private val savedFragment = SavedWallpapersFragment()

    private var currentFragment: Fragment = mainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.container, mainFragment)
                add(R.id.container, savedFragment)
                hide(savedFragment)
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main -> {
                    supportFragmentManager.commit {
                        hide(currentFragment)
                        show(mainFragment)
                    }
                    currentFragment = mainFragment
                    true
                }

                R.id.saved -> {
                    supportFragmentManager.commit {
                        hide(currentFragment)
                        show(savedFragment)
                    }
                    currentFragment = savedFragment
                    true
                }
            }
            true
        }



//        binding.bottomNavigation.setOnItemReselectedListener { item ->
//            when(item.itemId) {
//                R.id.mainFragment -> {
//                    Log.d("MyHelper", "reselect")
//                }
//            }
//        }
    }

    override fun show() {
//        binding.bottomNavigation.visibility = View.GONE
        binding.bottomNavigation.animate()
            .translationY(0f)
            .setDuration(500)
            .start()
    }

    override fun hide() {
        binding.bottomNavigation.animate()
            .translationY(binding.bottomNavigation.height.toFloat())
            .setDuration(500)
            .start()
//        binding.bottomNavigation.visibility = View.VISIBLE

    }
}