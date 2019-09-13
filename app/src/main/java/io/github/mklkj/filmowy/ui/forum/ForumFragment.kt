package io.github.mklkj.filmowy.ui.forum

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentForumBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class ForumFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val viewModel: ForumViewModel by viewModels { vmFactory }

    @Inject
    lateinit var dataAdapter: ForumListAdapter

    private val args: ForumFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            dataSource.url = args.url
            threads.observe(this@ForumFragment, Observer { dataAdapter.submitList(it) })
            networkState.observe(this@ForumFragment, Observer { dataAdapter.setNetworkState(it) })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return FragmentForumBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            initializeAdapter(this)
        }.root
    }

    private fun initializeAdapter(binding: FragmentForumBinding) {
        binding.forumRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }

        dataAdapter.retryCallback = { viewModel.retry() }
        dataAdapter.openThreadCallback = {
            binding.root.findNavController().navigate(ForumFragmentDirections.actionForumFragmentToThreadFragment(it.url))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.forum, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.forum_open_in_browser -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(args.url)))
                true
            }
            else -> false
        }
    }
}
