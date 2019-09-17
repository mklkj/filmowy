package io.github.mklkj.filmowy.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.databinding.FragmentNewsBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class NewsFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val vm: NewsViewModel by viewModels { vmFactory }

    @Inject
    lateinit var dataAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.news.observe(this, Observer { dataAdapter.submitList(it) })
        vm.networkState.observe(this, Observer { dataAdapter.setNetworkState(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentNewsBinding>(inflater, R.layout.fragment_news, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
            initializeAdapter(this)
        }.root
    }

    private fun initializeAdapter(binding: FragmentNewsBinding) {
        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }

        dataAdapter.retryCallback = { vm.retry() }
        dataAdapter.openArticleCallback = { it, image, position ->
            findNavController().navigate(
                NewsFragmentDirections.actionNewsFragmentToArticleFragment(News.get(it.id, it.title, it.newsImageUrl, it.publicationTime), position),
                FragmentNavigatorExtras(
                    image to image.transitionName
                )
            )
        }
    }
}
