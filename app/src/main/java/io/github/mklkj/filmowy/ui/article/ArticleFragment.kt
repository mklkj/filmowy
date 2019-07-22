package io.github.mklkj.filmowy.ui.article

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.databinding.FragmentArticleBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class ArticleFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val vm by lazy { ViewModelProviders.of(this, vmFactory).get(ArticleViewModel::class.java) }

    private val args by navArgs<ArticleFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
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
                    content = "",
                    lead = null
                )
            }
        }

        binding.articleImage.transitionName = "news_image_${args.position}"

        vm.getArticle((args.article as NewsLead).id).observe(viewLifecycleOwner, Observer { if (it.content.isNotEmpty()) binding.article = it })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.article, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.article_open_in_browser -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://m.filmweb.pl/news/-${(args.article as NewsLead).id}")))
                true
            }
            else -> false
        }
    }
}
