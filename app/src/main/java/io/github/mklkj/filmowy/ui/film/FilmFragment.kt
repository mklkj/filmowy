package io.github.mklkj.filmowy.ui.film

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.encodeName
import io.github.mklkj.filmowy.api.toUrl
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

                seasonsButton.setOnClickListener { _ ->
                    findNavController().navigate(FilmFragmentDirections.actionFilmFragmentToEpisodesFragment(it, it.filmInfo?.seasonsCount ?: 1))
                }
                forumButton.setOnClickListener { _ ->
                    findNavController().navigate(FilmFragmentDirections.actionFilmFragmentToForumFragment("film", it.encodeName()))
                }
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
                args.film.run { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(toUrl()))) }
                true
            }
            else -> false
        }
    }
}
