package io.github.mklkj.filmowy.ui.forum

import androidx.recyclerview.widget.DiffUtil
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.ForumThread
import io.github.mklkj.filmowy.base.BasePagedListAdapter
import io.github.mklkj.filmowy.databinding.ItemForumThreadBinding
import javax.inject.Inject

class ForumListAdapter @Inject constructor() :
    BasePagedListAdapter<ForumThread, ItemForumThreadBinding>(diffCallback) {

    lateinit var openThreadCallback: (ForumThread) -> Unit

    override fun getListItemViewType() = R.layout.item_forum_thread

    override fun onBindListViewHolder(holder: ViewHolder<ItemForumThreadBinding>, position: Int, listItem: ForumThread?) {
        holder.binding.apply {
            item = listItem
            threadLayout.setOnClickListener { listItem?.let { openThreadCallback(it) } }
            executePendingBindings()
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ForumThread>() {
            override fun areItemsTheSame(oldItem: ForumThread, newItem: ForumThread): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: ForumThread, newItem: ForumThread): Boolean {
                return oldItem == newItem
            }
        }
    }
}
