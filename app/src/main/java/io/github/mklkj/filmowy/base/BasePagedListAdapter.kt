package io.github.mklkj.filmowy.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.databinding.ItemNetworkStateBinding

abstract class BasePagedListAdapter<T, B : ViewDataBinding>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    private var networkStateValue: NetworkState? = null

    lateinit var retryCallback: () -> Unit

    private fun hasExtraRow() = networkStateValue != null && networkStateValue != NetworkState.LOADED

    @LayoutRes
    abstract fun getListItemViewType(): Int

    abstract fun onBindListViewHolder(holder: ViewHolder<B>, position: Int, listItem: T?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_network_state -> NetworkStateViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false), retryCallback
            )
            getListItemViewType() -> ViewHolder<B>(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            getListItemViewType() -> onBindListViewHolder(holder as ViewHolder<B>, position, getItem(position))
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bindTo(networkStateValue)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) R.layout.item_network_state
        else getListItemViewType()
    }

    override fun getItemCount() = super.getItemCount() + if (hasExtraRow()) 1 else 0

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList?.size == 0) return

        val previousState = networkStateValue
        val hadExtraRow = hasExtraRow()
        networkStateValue = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) notifyItemRemoved(super.getItemCount())
            else notifyItemInserted(super.getItemCount())
        } else if (hasExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    class ViewHolder<B : ViewDataBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)

    class NetworkStateViewHolder(private val b: ItemNetworkStateBinding, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(b.root) {

        fun bindTo(networkState: NetworkState?) {
            b.state = networkState
            b.retryLoadingButton.setOnClickListener { retryCallback() }
            b.executePendingBindings()
        }
    }
}
