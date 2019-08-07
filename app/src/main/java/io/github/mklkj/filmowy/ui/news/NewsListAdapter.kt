package io.github.mklkj.filmowy.ui.news

import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.base.BasePagedListAdapter
import io.github.mklkj.filmowy.databinding.ItemNewsBinding
import javax.inject.Inject

class NewsListAdapter @Inject constructor() : BasePagedListAdapter<NewsLead, ItemNewsBinding>(diffCallback) {

    lateinit var openArticleCallback: (NewsLead, ImageView, Int) -> Unit

    override fun getListItemViewType() = R.layout.item_news

    override fun onBindListViewHolder(holder: ViewHolder<ItemNewsBinding>, position: Int, listItem: NewsLead?) {
        holder.binding.apply {
            item = listItem
            itemNewsImage.transitionName = "news_image_${holder.adapterPosition}"
            itemNewsContainer.setOnClickListener { listItem?.let { openArticleCallback(it, itemNewsImage, holder.layoutPosition) } }
            executePendingBindings()
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
}
