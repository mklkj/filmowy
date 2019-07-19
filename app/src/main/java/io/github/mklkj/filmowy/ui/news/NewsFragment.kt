package io.github.mklkj.filmowy.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentNewsBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class NewsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dataAdapter: NewsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vm = ViewModelProviders.of(this, viewModelFactory).get(NewsViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentNewsBinding>(inflater, R.layout.fragment_news, container, false)
            .apply {
                viewModel = vm
                lifecycleOwner = viewLifecycleOwner
            }

        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }

        dataAdapter.retryCallback = { vm.retry() }
        dataAdapter.openArticleCallback = {
            binding.root.findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToArticleFragment(it))
        }

        vm.news.observe(viewLifecycleOwner, Observer { dataAdapter.submitList(it) })
        vm.networkState.observe(viewLifecycleOwner, Observer { dataAdapter.setNetworkState(it) })

        return binding.root
    }
}
