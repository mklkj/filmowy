package io.github.mklkj.filmowy.ui.film

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentFilmBinding
import javax.inject.Inject

@AndroidEntryPoint
class FilmFragment : BaseFragment<FragmentFilmBinding>(R.layout.fragment_film) {

    override val viewModel: FilmViewModel by viewModels()

    private val args: FilmFragmentArgs by navArgs()

    @Inject
    lateinit var filmAdapter: FilmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentTitle("")
        viewModel.loadData(args.url)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        binding.vm = viewModel

        filmAdapter.onEpisodesButtonCallback = viewModel::navigateToEpisodes
        filmAdapter.onForumButtonCallback = viewModel::navigateToForum
        filmAdapter.onForumThreadCallback = viewModel::navigateToTopic

        with(binding.recycler) {
            adapter = filmAdapter
            layoutManager = LinearLayoutManager(context)
        }
        viewModel.film.observe(viewLifecycleOwner) {
            setFragmentTitle(it.filmType)
            with(filmAdapter) {
                film = it
                notifyDataSetChanged()
            }
        }
        viewModel.vote.observe(viewLifecycleOwner) {
            with(filmAdapter) {
                vote = it
                notifyItemChanged(2)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.film, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = if (item.itemId == R.id.film_open_in_browser) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(args.url)))
        true
    } else false
}
