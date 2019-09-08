package io.github.mklkj.filmowy.ui.film.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.mklkj.filmowy.api.pojo.FilmEpisode
import io.github.mklkj.filmowy.databinding.ItemEpisodeBinding
import javax.inject.Inject

class EpisodesListAdapter @Inject constructor() : ListAdapter<FilmEpisode, EpisodesListAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(val binding: ItemEpisodeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            episode = getItem(position)
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
