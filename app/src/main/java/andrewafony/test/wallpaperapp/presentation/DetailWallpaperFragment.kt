package andrewafony.test.wallpaperapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andrewafony.test.wallpaperapp.R
import andrewafony.test.wallpaperapp.core.BaseFragment
import andrewafony.test.wallpaperapp.databinding.FragmentDetailWallpaperBinding

class DetailWallpaperFragment : BaseFragment<FragmentDetailWallpaperBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailWallpaperBinding
        get() = FragmentDetailWallpaperBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}