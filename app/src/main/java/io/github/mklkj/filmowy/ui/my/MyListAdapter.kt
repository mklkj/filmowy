package io.github.mklkj.filmowy.ui.my

import androidx.recyclerview.widget.DiffUtil
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.FriendVoteFilmEvent
import io.github.mklkj.filmowy.base.BasePagedListAdapter
import io.github.mklkj.filmowy.databinding.ItemFriendVoteBinding
import javax.inject.Inject

class MyListAdapter @Inject constructor() : BasePagedListAdapter<FriendVoteFilmEvent, ItemFriendVoteBinding>(diffCallback) {

    override fun getListItemViewType() = R.layout.item_friend_vote

    override fun onBindListViewHolder(holder: ViewHolder<ItemFriendVoteBinding>, position: Int, listItem: FriendVoteFilmEvent?) {
        holder.binding.apply {
            item = listItem
            executePendingBindings()
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
}
