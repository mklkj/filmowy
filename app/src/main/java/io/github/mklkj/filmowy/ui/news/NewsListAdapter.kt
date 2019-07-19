package io.github.mklkj.filmowy.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.databinding.ItemNewsBinding
import io.github.mklkj.filmowy.ui.NetworkStateViewHolder
import javax.inject.Inject

class NewsListAdapter @Inject constructor() : PagedListAdapter<NewsLead, RecyclerView.ViewHolder>(diffCallback) {

    private var networkState: NetworkState? = null

    lateinit var retryCallback: () -> Unit

    lateinit var openArticleCallback: (NewsLead) -> Unit

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) R.layout.item_network_state
        else R.layout.item_news
    }

    override fun getItemCount() = super.getItemCount() + if (hasExtraRow()) 1 else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_network_state -> NetworkStateViewHolder.create(parent, viewType, retryCallback)
            R.layout.item_news -> ViewHolder.create(parent, viewType, openArticleCallback)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_news -> (holder as ViewHolder).bindTo(getItem(position))
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<NewsLead>() {
            override fun areItemsTheSame(oldItem: NewsLead, newItem: NewsLead): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewsLead, newItem: NewsLead): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList?.size == 0) return

        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) notifyItemRemoved(super.getItemCount())
            else notifyItemInserted(super.getItemCount())
        } else if (hasExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    class ViewHolder(private val binding: ItemNewsBinding, private val openArticleCallback: (NewsLead) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(news: NewsLead?) {
            binding.item = news
            binding.itemNewsContainer.setOnClickListener { news?.let(openArticleCallback) }
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup, viewType: Int, callBack: (NewsLead) -> Unit): ViewHolder {
                return ViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false),
                    callBack
                )
            }
        }
    }
}
