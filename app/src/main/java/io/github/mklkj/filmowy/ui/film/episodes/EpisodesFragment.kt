package io.github.mklkj.filmowy.ui.film.episodes

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentEpisodesBinding

class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    private val args: EpisodesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewPager.adapter = EpisodesPagerAdapter(childFragmentManager, args.film)
            viewPager.currentItem = args.film.filmInfo?.seasonsCount ?: 1 - 1
            tabLayout.setupWithViewPager(viewPager)
        }
    }
}
