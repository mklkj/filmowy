package io.github.mklkj.filmowy.ui.film.episodes

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.ui.film.episodes.tab.EpisodesTabFragment

class EpisodesPagerAdapter(fm: FragmentManager, private val film: Film): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = EpisodesTabFragment().apply {
        arguments = Bundle().apply {
            putSerializable("film", film)
            putInt("seasonNumber", position + 1)
        }
    }

    override fun getCount() = film.filmInfo?.seasonsCount ?: 1

    override fun getPageTitle(position: Int): CharSequence? {
        return "Sezon ${position + 1}"
    }
}
