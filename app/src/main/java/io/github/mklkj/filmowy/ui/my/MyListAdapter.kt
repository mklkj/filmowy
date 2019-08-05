package io.github.mklkj.filmowy.ui.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.FriendVoteFilmEvent
import io.github.mklkj.filmowy.databinding.ItemFriendVoteBinding
import io.github.mklkj.filmowy.ui.NetworkStateViewHolder
import javax.inject.Inject

class MyListAdapter @Inject constructor() : PagedListAdapter<FriendVoteFilmEvent, RecyclerView.ViewHolder>(diffCallback) {

    private var networkState: NetworkState? = null

    lateinit var retryCallback: () -> Unit

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) R.layout.item_network_state
        else R.layout.item_friend_vote
    }

    override fun getItemCount() = super.getItemCount() + if (hasExtraRow()) 1 else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_network_state -> NetworkStateViewHolder.create(parent, viewType, retryCallback)
            R.layout.item_friend_vote -> ViewHolder.create(parent, viewType)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_friend_vote -> (holder as ViewHolder).bindTo(getItem(position))
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<FriendVoteFilmEvent>() {
            override fun areItemsTheSame(oldItem: FriendVoteFilmEvent, newItem: FriendVoteFilmEvent): Boolean {
                return oldItem.filmId == newItem.filmId && oldItem.friendUserId == newItem.friendUserId
            }

            override fun areContentsTheSame(oldItem: FriendVoteFilmEvent, newItem: FriendVoteFilmEvent): Boolean {
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

    class ViewHolder(private val binding: ItemFriendVoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: FriendVoteFilmEvent?) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
            }
        }
    }

}
