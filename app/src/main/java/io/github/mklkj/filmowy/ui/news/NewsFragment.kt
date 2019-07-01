package io.github.mklkj.filmowy.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentNewsBinding
import io.github.mklkj.filmowy.di.ViewModelFactory
import javax.inject.Inject

class NewsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dataAdapter: NewsListAdapter

    private lateinit var vm: NewsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vm = ViewModelProviders.of(this, viewModelFactory).get(NewsViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentNewsBinding>(inflater, R.layout.fragment_news, container, false)
            .apply {
                viewModel = vm
                lifecycleOwner = this@NewsFragment
            }

        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }

        vm.news.observe(this, Observer { dataAdapter.submitList(it) })

        return binding.root
    }
}
