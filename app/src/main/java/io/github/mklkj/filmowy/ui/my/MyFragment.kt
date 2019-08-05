package io.github.mklkj.filmowy.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentMyBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import timber.log.Timber
import javax.inject.Inject

class MyFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val vm by lazy { ViewModelProviders.of(this, vmFactory).get(MyViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentMyBinding>(inflater, R.layout.fragment_my, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm.getFriendsVotes().observe(viewLifecycleOwner, Observer {
                Timber.d(it.toString())
            })
        }.root
    }
}
