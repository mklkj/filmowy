package io.github.mklkj.filmowy.ui.forum

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ForumPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = ForumTabFragment().apply {
        arguments = Bundle().apply {
            putInt("pageSize", 10)
            putString("url", "https://www.filmweb.pl/forum/" + when(position) {
                0 -> "filmy"
                1 -> "seriale"
                else -> "portal+filmweb.pl"
            })
        }
    }

    override fun getCount() = 3

    override fun getPageTitle(position: Int) = when(position) {
        0 -> "Filmy"
        1 -> "Seriale"
        else -> "Portal"
    }
}
