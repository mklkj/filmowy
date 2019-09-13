package io.github.mklkj.filmowy.ui.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.databinding.FragmentForumBinding

class ForumFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentForumBinding.inflate(inflater, container, false).apply {
            viewPager.adapter = ForumPagerAdapter(childFragmentManager)
            tabLayout.setupWithViewPager(viewPager)
        }.root
    }
}
