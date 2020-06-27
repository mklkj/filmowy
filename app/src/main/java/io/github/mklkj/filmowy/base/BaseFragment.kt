package io.github.mklkj.filmowy.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<DB : ViewDataBinding>(@LayoutRes private val layoutId: Int) : Fragment() {

    protected lateinit var binding: DB

    protected open val viewModel: BaseViewModel = BaseViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<DB>(inflater, layoutId, container, false).apply {
            lifecycleOwner = viewLifecycleOwner

            binding = this
        }.root
    }

    override fun onStart() {
        super.onStart()
        initNavCommandObserver()
    }

    private fun initNavCommandObserver() {
        viewModel.disposable.add(viewModel.navCommand.subscribe { direction ->
            with(findNavController()) {
                currentDestination?.getAction(direction.actionId)?.let { navigate(direction) }
            }
        })
    }
}
