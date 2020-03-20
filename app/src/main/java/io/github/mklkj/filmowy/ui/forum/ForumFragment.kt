package io.github.mklkj.filmowy.ui.forum

import android.os.Bundle
import android.view.View
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentForumBinding

class ForumFragment : BaseFragment<FragmentForumBinding>(R.layout.fragment_forum) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewPager.adapter = ForumPagerAdapter(childFragmentManager)
            tabLayout.setupWithViewPager(viewPager)
        }
    }
}
