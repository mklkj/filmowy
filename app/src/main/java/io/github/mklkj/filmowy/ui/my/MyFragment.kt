package io.github.mklkj.filmowy.ui.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentMyBinding
import javax.inject.Inject

class MyFragment : BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {

    override val viewModel: MyViewModel by viewModels { vmFactory }

    @Inject
    lateinit var dataAdapter: MyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.votes.observe(this, Observer { dataAdapter.submitList(it) })
        viewModel.networkState.observe(this, Observer { dataAdapter.setNetworkState(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.vm = viewModel

        initializeAdapter()
    }

    private fun initializeAdapter() {
        binding.myRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }

        dataAdapter.retryCallback = { viewModel.retry() }
    }
}
