package io.github.mklkj.filmowy.ui.film

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.pojo.SearchResult
import io.github.mklkj.filmowy.databinding.FragmentFilmBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class FilmFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val args by navArgs<FilmFragmentArgs>()

    private val vm by lazy { ViewModelProviders.of(this, vmFactory).get(FilmViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val binding = DataBindingUtil.inflate<FragmentFilmBinding>(inflater, R.layout.fragment_film, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
            film = (args.film as SearchResult.Film).run {
                Film(
                    title = title,
                    year = year,
                    filmInfo = null,
                    imagePath = poster,
                    duration = null,
                    avgRate = .0,
                    votesCount = 0
                )
            }
        }

        vm.getFullFilmInfo((args.film as SearchResult.Film).id).observe(this, Observer {
            binding.film = it
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.film, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.film_open_in_browser -> {
                (args.film as SearchResult.Film).run {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://m.filmweb.pl/film/${title.encode()}-$year-$id")))
                }
                true
            }
            else -> false
        }
    }

    private fun String.encode() = replace(" ", "+").replace("?", "")
}
