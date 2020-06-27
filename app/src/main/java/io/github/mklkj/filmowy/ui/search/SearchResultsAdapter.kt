package io.github.mklkj.filmowy.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.SearchResult
import io.github.mklkj.filmowy.api.pojo.SearchResult.Type.*
import io.github.mklkj.filmowy.databinding.ItemSearchFilmBinding
import io.github.mklkj.filmowy.databinding.ItemSearchPersonBinding
import io.github.mklkj.filmowy.databinding.ItemSearchResultBinding
import javax.inject.Inject

class SearchResultsAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var openFilmCallback: (SearchResult.Film) -> Unit = {}

    var openPersonCallback: (SearchResult.Person) -> Unit = {}

    var items = emptyList<SearchResult>()

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = when (items[position].type) {
        FILM, SERIAL -> R.layout.item_search_film
//        GAME -> TODO()
        PERSON -> R.layout.item_search_person
//        CHANNEL -> TODO()
//        CINEMA -> TODO()
        else -> R.layout.item_search_result
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_search_film -> FilmViewHolder(getBinding(parent, viewType), openFilmCallback)
            R.layout.item_search_person -> PersonViewHolder(getBinding(parent, viewType), openPersonCallback)
            else -> ViewHolder(getBinding(parent, viewType))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_search_film -> (holder as FilmViewHolder).bindTo(items[position] as SearchResult.Film)
            R.layout.item_search_person -> (holder as PersonViewHolder).bindTo(items[position] as SearchResult.Person)
            else -> (holder as ViewHolder).bindTo(items[position])
        }
    }

    class ViewHolder(private val binding: ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: SearchResult) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    class FilmViewHolder(private val binding: ItemSearchFilmBinding, private val callback: (SearchResult.Film) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: SearchResult.Film) {
            binding.item = item
            binding.root.setOnClickListener { callback(item) }
            binding.executePendingBindings()
        }
    }

    class PersonViewHolder(private val binding: ItemSearchPersonBinding, private val callback: (SearchResult.Person) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: SearchResult.Person) {
            binding.person = item
            binding.root.setOnClickListener { callback(item) }
            binding.executePendingBindings()
        }
    }

    private fun <T : ViewDataBinding> getBinding(parent: ViewGroup, viewType: Int): T {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
    }
}
