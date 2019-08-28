package io.github.mklkj.filmowy.ui.article

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentArticleBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class ArticleFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val viewModel: ArticleViewModel by viewModels { vmFactory }

    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadArticle(args.article)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return FragmentArticleBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel

            article = args.article
            articleImage.transitionName = "news_image_${args.position}"

            viewModel.article.observe(viewLifecycleOwner, Observer { article = it })
        }.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.article, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.article_open_in_browser -> {
                startActivity(
                    Intent(ACTION_VIEW, Uri.parse("https://m.filmweb.pl/news/${Uri.encode(args.article.title)}-${args.article.newsId}"))
                )
                true
            }
            else -> false
        }
    }
}
