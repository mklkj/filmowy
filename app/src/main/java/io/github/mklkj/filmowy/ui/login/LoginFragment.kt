package io.github.mklkj.filmowy.ui.login

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.EditorInfo.IME_NULL
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import io.github.mklkj.filmowy.NavGraphDirections
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentLoginBinding
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override val viewModel: LoginViewModel by viewModels { vmFactory }

    @Inject
    lateinit var navigationLoginHelper: NavigationLoginHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            vm = viewModel
            viewModel.networkState.observe(viewLifecycleOwner, Observer {
                if (it.status == NetworkState.LOADING.status) activity?.hideSoftInput()
            })
            viewModel.user.observe(viewLifecycleOwner, Observer {
                it?.let {
                    activity?.run { navigationLoginHelper.updateNavigationHeader(findViewById(R.id.navView)) }
                    findNavController().navigate(NavGraphDirections.actionGlobalNewsFragment())
                }
            })
            loginFormPass.setOnEditorActionListener { _, id, _ ->
                if (id == IME_ACTION_DONE || id == IME_NULL) loginFormSignIn.callOnClick() else false
            }
        }
    }

    private fun Activity.hideSoftInput() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?)?.run {
            hideSoftInputFromWindow(window.decorView.applicationWindowToken, 0)
        }
    }
}
