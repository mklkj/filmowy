package io.github.mklkj.filmowy.ui.article

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentArticleBinding
import io.github.mklkj.filmowy.ui.HtmlImageGetter
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class ArticleFragment : DaggerFragment() {

    @Inject
    lateinit var htmlImageGetter: HtmlImageGetter

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val vm: ArticleViewModel by viewModels { vmFactory }

    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.loadArticle(args.article)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val binding = DataBindingUtil.inflate<FragmentArticleBinding>(inflater, R.layout.fragment_article, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
            article = args.article
        }

        binding.articleImage.transitionName = "news_image_${args.position}"
        vm.article.observe(viewLifecycleOwner, Observer { binding.article = it })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.article, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.article_open_in_browser -> {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://m.filmweb.pl/news/${Uri.encode(args.article.title)}-${args.article.newsId}")
                    )
                )
                true
            }
            else -> false
        }
    }
}
