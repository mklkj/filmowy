package io.github.mklkj.filmowy.ui.forum

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentForumTabBinding
import javax.inject.Inject

class ForumTabFragment : BaseFragment<FragmentForumTabBinding>(R.layout.fragment_forum_tab) {

    private val viewModel: ForumViewModel by viewModels { vmFactory }

    @Inject
    lateinit var dataAdapter: ForumListAdapter

    private val args: ForumTabFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            pageSize = args.pageSize
            dataSource.url = args.url
            threads.observe(this@ForumTabFragment, Observer { dataAdapter.submitList(it) })
            networkState.observe(this@ForumTabFragment, Observer { dataAdapter.setNetworkState(it) })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        binding.vm = viewModel
        initializeAdapter()
    }

    private fun initializeAdapter() {
        with(binding.forumRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }

        dataAdapter.retryCallback = { viewModel.retry() }
        dataAdapter.openThreadCallback = {
            findNavController().navigate(
                when (parentFragment) {
                    is ForumFragment -> ForumFragmentDirections.actionForumFragmentToThreadFragment2(it.url)
                    else -> ForumTabFragmentDirections.actionForumFragmentToThreadFragment(it.url)
                }
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.forum, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = if (item.itemId == R.id.forum_open_in_browser) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(args.url)))
        true
    } else false
}
