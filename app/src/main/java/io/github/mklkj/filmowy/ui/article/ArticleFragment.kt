package io.github.mklkj.filmowy.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.databinding.FragmentArticleBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class ArticleFragment : DaggerFragment() {

    private val args by navArgs<ArticleFragmentArgs>()

    @Inject
    lateinit var vmFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vm = ViewModelProviders.of(this, vmFactory).get(ArticleViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentArticleBinding>(inflater, R.layout.fragment_article, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
            article = (args.article as NewsLead).let {
                // TODO
                News(
                    title = it.title,
                    newsImageUrl = it.newsImageUrl,
                    author = null,
                    source = null,
                    commentsCount = 0,
                    publicationTime = it.publicationTime,
                    content = it.lead,
                    lead = it.lead
                )
            }
        }

        vm.getArticle((args.article as NewsLead).id).observe(viewLifecycleOwner, Observer { if (it.content.isNotEmpty()) binding.article = it })

        return binding.root
    }
}
