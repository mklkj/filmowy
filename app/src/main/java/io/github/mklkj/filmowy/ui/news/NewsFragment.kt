package io.github.mklkj.filmowy.ui.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentNewsBinding
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>(R.layout.fragment_news) {

    override val viewModel: NewsViewModel by viewModels()

    @Inject
    lateinit var dataAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.news.observe(this, Observer { dataAdapter.submitList(it) })
        viewModel.networkState.observe(this, Observer { dataAdapter.setNetworkState(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.vm = viewModel

        initializeAdapter()
    }

    private fun initializeAdapter() {
        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }

        dataAdapter.retryCallback = { viewModel.retry() }
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
