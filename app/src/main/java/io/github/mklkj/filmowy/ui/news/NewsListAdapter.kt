package io.github.mklkj.filmowy.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.NewsLead

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    private var items: List<NewsLead> = emptyList()

    fun loadItems(newItems: List<NewsLead>) {
        items = newItems
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news, parent, false) as TextView
        )
    }

    inner class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}
