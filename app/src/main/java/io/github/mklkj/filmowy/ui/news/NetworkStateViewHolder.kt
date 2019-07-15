package io.github.mklkj.filmowy.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.databinding.ItemNetworkStateBinding

class NetworkStateViewHolder(private val binding: ItemNetworkStateBinding, private val retryCallback: () -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(networkState: NetworkState?) {
        binding.state = networkState
        binding.retryLoadingButton.setOnClickListener { retryCallback() }
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int, retryCallback: () -> Unit): NetworkStateViewHolder {
            return NetworkStateViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), viewType, parent, false
                ), retryCallback
            )
        }
    }
}
