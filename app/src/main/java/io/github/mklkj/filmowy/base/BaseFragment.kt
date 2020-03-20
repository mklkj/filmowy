package io.github.mklkj.filmowy.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

abstract class BaseFragment<DB : ViewDataBinding>(@LayoutRes private val layoutId: Int) : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    protected lateinit var binding: DB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<DB>(inflater, layoutId, container, false).apply {
            lifecycleOwner = viewLifecycleOwner

            binding = this
        }.root
    }
}
