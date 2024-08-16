package andrewafony.test.wallpaperapp.core

import andrewafony.test.wallpaperapp.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(
    private val showBottomNavigation: Boolean
): Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (!showBottomNavigation)
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        if (!showBottomNavigation)
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

