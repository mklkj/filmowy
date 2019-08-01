package io.github.mklkj.filmowy.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentLoginBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val vm by lazy { ViewModelProviders.of(this, vmFactory).get(LoginViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
        }.root
    }
}
