package io.github.mklkj.filmowy.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.getNewsImageUrl
import io.github.mklkj.filmowy.api.pojo.NewsLead
import kotlinx.android.synthetic.main.item_news.view.*
import javax.inject.Inject

class NewsListAdapter @Inject constructor(
    private val picasso: Picasso
) : PagedListAdapter<NewsLead, RecyclerView.ViewHolder>(userDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_news, parent, false)
        return ViewHolder(view, picasso)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindTo(getItem(position))
    }

    companion object {
        val userDiffCallback = object : DiffUtil.ItemCallback<NewsLead>() {
            override fun areItemsTheSame(oldItem: NewsLead, newItem: NewsLead): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewsLead, newItem: NewsLead): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(view: View, private val picasso: Picasso) : RecyclerView.ViewHolder(view) {

        fun bindTo(news: NewsLead?) {
            itemView.itemNewsTitle.text = news?.title
            itemView.itemNewsLead.text = news?.lead
            itemView.itemNewsDate.text = news?.publicationTime.toString()
            picasso.load(news?.newsImageUrl?.getNewsImageUrl(640)).into(itemView.itemNewsImage)
        }
    }
}
