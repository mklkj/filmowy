package io.github.mklkj.filmowy.ui.login

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.NavGraphDirections
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.databinding.FragmentLoginBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    @Inject
    lateinit var navigationLoginHelper: NavigationLoginHelper

    private val vm by lazy { ViewModelProviders.of(this, vmFactory).get(LoginViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
            vm.networkState.observe(viewLifecycleOwner, Observer {
                if (it.status == NetworkState.LOADING.status) activity?.hideSoftInput()
            })
            vm.user.observe(viewLifecycleOwner, Observer {
                it?.let {
                    activity?.run { navigationLoginHelper.updateNavigationHeader(findViewById(R.id.navView)) }
                    findNavController().navigate(NavGraphDirections.actionGlobalNewsFragment())
                }
            })
        }.root
    }

    private fun Activity.hideSoftInput() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?)?.run {
            hideSoftInputFromWindow(window.decorView.applicationWindowToken, 0)
        }
    }
}
