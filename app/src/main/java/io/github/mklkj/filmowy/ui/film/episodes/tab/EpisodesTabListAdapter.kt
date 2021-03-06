package io.github.mklkj.filmowy.ui.film.episodes.tab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.mklkj.filmowy.api.pojo.FilmEpisode
import io.github.mklkj.filmowy.databinding.ItemEpisodeBinding
import javax.inject.Inject

class EpisodesTabListAdapter @Inject constructor() : ListAdapter<FilmEpisode, EpisodesTabListAdapter.ViewHolder>(diffCallback) {

    var setEpisodeVoteCallback: (Int, Int) -> Unit = { _, _ -> }

    class ViewHolder(val binding: ItemEpisodeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            episode = getItem(position)
            noRate.setOnClickListener { setEpisodeVoteCallback(getItem(position).id, getItem(position).rate) }
            rateNumber.setOnClickListener { setEpisodeVoteCallback(getItem(position).id, getItem(position).rate) }
            rateWatched.setOnClickListener { setEpisodeVoteCallback(getItem(position).id, getItem(position).rate) }
            executePendingBindings()
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<FilmEpisode>() {
            override fun areItemsTheSame(oldItem: FilmEpisode, newItem: FilmEpisode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FilmEpisode, newItem: FilmEpisode): Boolean {
                return oldItem == newItem
            }
        }
    }
}
