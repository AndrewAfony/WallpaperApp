package andrewafony.test.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

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
            (requireActivity() as BottomNavigationController).hide()
    }

    override fun onPause() {
        super.onPause()
        if (!showBottomNavigation)
            (requireActivity() as BottomNavigationController).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

