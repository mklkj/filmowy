package io.github.mklkj.filmowy.ui.film.episodes.tab

import android.app.AlertDialog
import android.content.DialogInterface.BUTTON_POSITIVE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentEpisodesTabBinding
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import javax.inject.Inject

@AndroidEntryPoint
class EpisodesTabFragment : BaseFragment<FragmentEpisodesTabBinding>(R.layout.fragment_episodes_tab) {

    @Inject
    lateinit var dataAdapter: EpisodesTabListAdapter

    override val viewModel: EpisodesTabViewModel by viewModels()

    private val args: EpisodesTabFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadEpisodes(args.film, args.seasonNumber)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        with(binding) {
            vm = viewModel
            episodesSwipeRefreshLayout.setOnRefreshListener { viewModel.loadEpisodes(args.film, args.seasonNumber) }
            dataAdapter.setEpisodeVoteCallback = { id, rate ->
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
            viewModel.episodes.observe(viewLifecycleOwner) {
                dataAdapter.submitList(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.film, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = if (item.itemId == R.id.film_open_in_browser) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(args.film.url + "/episodes")))
        true
    } else false
}
