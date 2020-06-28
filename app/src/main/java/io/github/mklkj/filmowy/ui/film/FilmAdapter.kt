package io.github.mklkj.filmowy.ui.film

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.Resource
import io.github.mklkj.filmowy.api.ajax.FilmVote
import io.github.mklkj.filmowy.api.pojo.FilmFullInfo
import io.github.mklkj.filmowy.databinding.*
import javax.inject.Inject

class FilmAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var film: FilmFullInfo

    var vote: Resource<FilmVote>? = null

    var onEpisodesButtonCallback: () -> Unit = {}

    var onForumThreadCallback: (FilmFullInfo.ForumTopic) -> Unit = {}

    var onForumButtonCallback: () -> Unit = {}

    override fun getItemCount() = 5 + film.lastForumTopics.size

    override fun getItemViewType(position: Int) = when (position) {
        0 -> R.layout.item_film_cover
        1 -> R.layout.item_film_poster
        2 -> R.layout.item_film_rating
        3 -> R.layout.item_film_forum_header
        in 4..(itemCount - 2) -> R.layout.item_film_forum_thread
        itemCount - 1 -> R.layout.item_film_forum_footer
        else -> throw IllegalStateException("Unknown view type for position $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_film_cover -> CoverViewHolder(ItemFilmCoverBinding.inflate(inflater, parent, false))
            R.layout.item_film_poster -> PosterViewHolder(ItemFilmPosterBinding.inflate(inflater, parent, false))
            R.layout.item_film_rating -> RatingViewHolder(ItemFilmRatingBinding.inflate(inflater, parent, false))
            R.layout.item_film_forum_header -> ForumHeaderViewHolder(ItemFilmForumHeaderBinding.inflate(inflater, parent, false))
            R.layout.item_film_forum_thread -> ForumThreadViewHolder(ItemFilmForumThreadBinding.inflate(inflater, parent, false))
            R.layout.item_film_forum_footer -> ForumFooterViewHolder(ItemFilmForumFooterBinding.inflate(inflater, parent, false))
            else -> throw IllegalStateException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoverViewHolder -> bindCoverViewHolder(holder)
            is PosterViewHolder -> bindPosterViewHolder(holder)
            is RatingViewHolder -> bindRatingViewHolder(holder)
            is ForumHeaderViewHolder -> bindForumHeaderViewHolder(holder)
            is ForumThreadViewHolder -> bindForumThreadViewHolder(holder, position)
            is ForumFooterViewHolder -> bindForumFooterViewHolder(holder)
        }
    }

    private fun bindCoverViewHolder(holder: CoverViewHolder) {
        with(holder.binding) {
            item = film
            executePendingBindings()
        }
    }

    private fun bindPosterViewHolder(holder: PosterViewHolder) {
        with(holder.binding) {
            item = film
            executePendingBindings()

            seasons.setOnClickListener { onEpisodesButtonCallback() }
        }
    }

    private fun bindRatingViewHolder(holder: RatingViewHolder) {
        with(holder.binding) {
            item = vote?.data
            state = vote
            executePendingBindings()
        }
    }

    private fun bindForumHeaderViewHolder(holder: ForumHeaderViewHolder) {
        with(holder.binding) {
            item = film
            executePendingBindings()
        }
    }

    private fun bindForumThreadViewHolder(holder: ForumThreadViewHolder, position: Int) {
        val topic = film.lastForumTopics[position - 4]
        with(holder.binding) {
            item = topic
            executePendingBindings()

            root.setOnClickListener { onForumThreadCallback(topic) }
        }
    }

    private fun bindForumFooterViewHolder(holder: ForumFooterViewHolder) {
        with(holder.binding) {
            item = film
            executePendingBindings()

            forumButton.setOnClickListener { onForumButtonCallback() }
        }
    }

    private class CoverViewHolder(val binding: ItemFilmCoverBinding) : RecyclerView.ViewHolder(binding.root)

    private class PosterViewHolder(val binding: ItemFilmPosterBinding) : RecyclerView.ViewHolder(binding.root)

    private class RatingViewHolder(val binding: ItemFilmRatingBinding) : RecyclerView.ViewHolder(binding.root)

    //

    private class ForumHeaderViewHolder(val binding: ItemFilmForumHeaderBinding) : RecyclerView.ViewHolder(binding.root)

    private class ForumThreadViewHolder(val binding: ItemFilmForumThreadBinding) : RecyclerView.ViewHolder(binding.root)

    private class ForumFooterViewHolder(val binding: ItemFilmForumFooterBinding) : RecyclerView.ViewHolder(binding.root)
}
