package andrewafony.test.wallpaperapp

import andrewafony.test.wallpaperapp.core.BaseFragment
import andrewafony.test.wallpaperapp.databinding.ActivityMainBinding
import andrewafony.test.wallpaperapp.presentation.MainFragment
import andrewafony.test.wallpaperapp.presentation.SavedWallpapersFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fragments = mutableMapOf<String, Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    supportFragmentManager.switchFragments("home")
                    true
                }
                R.id.item_saved -> {
                    supportFragmentManager.switchFragments("saved")
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener { item ->
            // todo handle reselection
        }

        if (savedInstanceState == null) {
            fragments["home"] = MainFragment()
            fragments["saved"] = SavedWallpapersFragment()

            supportFragmentManager.commit {
                add(R.id.container, fragments["home"]!!, "home")
                add(R.id.container, fragments["saved"]!!, "saved").hide(fragments["saved"]!!)
            }
        } else {
            fragments["home"] = supportFragmentManager.findFragmentByTag("home")!!
            fragments["saved"] = supportFragmentManager.findFragmentByTag("saved")!!
        }
    }
}

private fun FragmentManager.switchFragments(tag: String) {
    commit {
        fragments.forEach { hide(it) }
        show(findFragmentByTag(tag)!!)
    }
}