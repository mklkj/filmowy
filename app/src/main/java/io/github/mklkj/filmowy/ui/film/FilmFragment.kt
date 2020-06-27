package io.github.mklkj.filmowy.ui.film

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentFilmBinding

@AndroidEntryPoint
class FilmFragment : BaseFragment<FragmentFilmBinding>(R.layout.fragment_film) {

    override val viewModel: FilmViewModel by viewModels()

    private val args: FilmFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadData(args.url)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        binding.vm = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.film, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = if (item.itemId == R.id.film_open_in_browser) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(args.url)))
        true
    } else false
}
