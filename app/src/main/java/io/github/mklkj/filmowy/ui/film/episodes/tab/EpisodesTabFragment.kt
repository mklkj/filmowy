package io.github.mklkj.filmowy.ui.film.episodes.tab

import android.app.AlertDialog
import android.content.DialogInterface.BUTTON_POSITIVE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.toUrl
import io.github.mklkj.filmowy.databinding.FragmentEpisodesTabBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import me.zhanghai.android.materialratingbar.MaterialRatingBar
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
        viewModel.loadEpisodes(args.film, args.seasonNumber)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return FragmentEpisodesTabBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel

            episodesSwipeRefreshLayout.setOnRefreshListener { viewModel.loadEpisodes(args.film, args.seasonNumber) }
            dataAdapter.setVoteCallback = { id, rate ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Oceń odcinek")
                    .setView(R.layout.dialog_vote)
                    .setNegativeButton("Usuń ocenę") { _, _ -> viewModel.setVote(args.film, args.seasonNumber, id, -1) }
                    .create()
                    .apply {
                        setOnShowListener {
                            findViewById<TextView>(R.id.dialogRatingText).text = getString(R.string.user_rating, rate)
                            findViewById<MaterialRatingBar>(R.id.dialogRatingBar).rating = rate.toFloat()
                            findViewById<MaterialRatingBar>(R.id.dialogRatingBar).onRatingChangeListener =
                                MaterialRatingBar.OnRatingChangeListener { _, rating ->
                                    findViewById<TextView>(R.id.dialogRatingText).text = getString(R.string.user_rating, rating.toInt())
                                }
                        }
                        setButton(BUTTON_POSITIVE, "Oceń") { _, _ ->
                            viewModel.setVote(args.film, args.seasonNumber, id, findViewById<MaterialRatingBar>(R.id.dialogRatingBar).rating.toInt())
                            dismiss()
                        }
                    }.show()

            }
            with(episodesRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                adapter = dataAdapter
            }
            viewModel.episodes.observe(viewLifecycleOwner, Observer { dataAdapter.submitList(it) })
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
