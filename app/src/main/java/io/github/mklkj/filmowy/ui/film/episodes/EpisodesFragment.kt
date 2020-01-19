package io.github.mklkj.filmowy.ui.film.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.databinding.FragmentEpisodesBinding

class EpisodesFragment : DaggerFragment() {

    private val args: EpisodesFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentEpisodesBinding.inflate(inflater, container, false).apply {
            viewPager.adapter = EpisodesPagerAdapter(childFragmentManager, args.film)
            tabLayout.setupWithViewPager(viewPager)
        }.root
    }
}
