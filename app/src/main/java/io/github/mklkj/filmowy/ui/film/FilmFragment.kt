package io.github.mklkj.filmowy.ui.film

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentFilmBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class FilmFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val viewModel: FilmViewModel by viewModels { vmFactory }

    private val args: FilmFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadFilmInfo(args.film.filmId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return FragmentFilmBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            film = args.film

            viewModel.film.observe(viewLifecycleOwner, Observer {
                args.film.title = it.title
                args.film.year = it.year
                film = it
            })
        }.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.film, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.film_open_in_browser -> {
                args.film.run {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://m.filmweb.pl/film/${title.encode()}-$year-$filmId")))
                }
                true
            }
            else -> false
        }
    }

    private fun String.encode() = replace(" ", "+").replace("?", "")
}
