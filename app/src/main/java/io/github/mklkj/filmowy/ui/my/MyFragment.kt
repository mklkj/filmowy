package io.github.mklkj.filmowy.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentMyBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class MyFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    @Inject
    lateinit var dataAdapter: MyListAdapter

    private val vm by lazy { ViewModelProviders.of(this, vmFactory).get(MyViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.votes.observe(this, Observer { dataAdapter.submitList(it) })
        vm.networkState.observe(this, Observer { dataAdapter.setNetworkState(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentMyBinding>(inflater, R.layout.fragment_my, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
            initializeAdapter(this)
        }.root
    }

    private fun initializeAdapter(binding: FragmentMyBinding) {
        binding.myRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }

        dataAdapter.retryCallback = { vm.retry() }
    }
}
