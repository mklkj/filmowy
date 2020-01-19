package io.github.mklkj.filmowy.ui.film.episodes.tab

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.encodeName
import io.github.mklkj.filmowy.api.toUrl
import io.github.mklkj.filmowy.databinding.FragmentEpisodesTabBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class EpisodesTabFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    @Inject
    lateinit var dataAdapter: EpisodesTabListAdapter

    private val viewModel: EpisodesTabViewModel by viewModels { vmFactory }

    private val args: EpisodesTabFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadEpisodes(args.film.encodeName(), args.seasonNumber)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return FragmentEpisodesTabBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel

            viewModel.episodes.observe(viewLifecycleOwner, Observer { dataAdapter.submitList(it) })
            episodesSwipeRefreshLayout.setOnRefreshListener { viewModel.loadEpisodes(args.film.encodeName(), args.seasonNumber) }
            with(episodesRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                adapter = dataAdapter
            }
        }.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.film, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.film_open_in_browser -> {
                args.film.run { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(toUrl() + "/episodes"))) }
                true
            }
            else -> false
        }
    }
}
