package andrewafony.test.wallpaperapp

import andrewafony.test.common.BottomNavigationController
import andrewafony.test.wallpaperapp.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), BottomNavigationController {

    private lateinit var binding: ActivityMainBinding

    private val navigation: MainNavigation by inject { parametersOf(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigation.init()
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            navigation.navigate(item.itemId)
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
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    override fun hide() {
        binding.bottomNavigation.visibility = View.GONE
    }
}