package andrewafony.test.wallpaperapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andrewafony.test.wallpaperapp.R
import andrewafony.test.wallpaperapp.core.BaseFragment
import andrewafony.test.wallpaperapp.databinding.FragmentSavedWallpapersBinding

class SavedWallpapersFragment : BaseFragment<FragmentSavedWallpapersBinding>(true) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSavedWallpapersBinding
        get() = FragmentSavedWallpapersBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}