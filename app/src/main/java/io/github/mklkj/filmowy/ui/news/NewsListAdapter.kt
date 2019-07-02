package io.github.mklkj.filmowy.ui.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.databinding.ItemNewsBinding
import javax.inject.Inject

class NewsListAdapter @Inject constructor() : PagedListAdapter<NewsLead, RecyclerView.ViewHolder>(userDiffCallback) {

    override fun getItemViewType(position: Int) = R.layout.item_news

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false), parent.context)
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

    class ViewHolder(private val binding: ItemNewsBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(news: NewsLead?) {
            binding.itemNewsContainer.setOnClickListener {
                Intent(Intent.ACTION_VIEW).let { intent ->
                    intent.data = Uri.parse("https://www.filmweb.pl/news/-${news?.id}")
                    context.startActivity(intent)
                }
            }
            binding.item = news
            binding.executePendingBindings()
        }
    }
}
