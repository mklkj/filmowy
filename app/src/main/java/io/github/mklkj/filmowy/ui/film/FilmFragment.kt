package io.github.mklkj.filmowy.ui.film

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.toUrl
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentFilmBinding

class FilmFragment : BaseFragment<FragmentFilmBinding>(R.layout.fragment_film) {

    private val viewModel: FilmViewModel by viewModels { vmFactory }

    private val args: FilmFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadFilmInfo(args.film.filmId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        with(binding) {
            vm = viewModel
            film = args.film

            viewModel.film.observe(viewLifecycleOwner, Observer {
                args.film.title = it.title
                args.film.year = it.year
                film = it

                seasonsButton.setOnClickListener { _ ->
                    findNavController().navigate(FilmFragmentDirections.actionFilmFragmentToEpisodesFragment(it))
                }
                forumButton.setOnClickListener { _ ->
                    findNavController().navigate(FilmFragmentDirections.actionFilmFragmentToForumFragment(it.filmInfo?.forumUrl ?: ""))
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.film, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = if (item.itemId == R.id.film_open_in_browser) {
        args.film.run { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(toUrl()))) }
        true
    } else false
}
