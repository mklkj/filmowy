package io.github.mklkj.filmowy.ui.login

import android.content.SharedPreferences
import android.view.View
import android.widget.Button
import com.google.android.material.navigation.NavigationView
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.UserData
import io.github.mklkj.filmowy.api.repository.LoginRepository
import io.github.mklkj.filmowy.databinding.HeaderNavigationUserBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationLoginHelper @Inject constructor(
    private val preferences: SharedPreferences,
    private val loginRepository: LoginRepository
) {

    var onUserClick: (UserData) -> Unit = {}

    var onLoginButtonCallback: (View) -> Unit = {}

    fun updateNavigationHeader(navView: NavigationView) {
        loadUserData().let {
            if (it == null) inflateLoginView(navView)
            else inflateUserView(navView)
        }
    }

    private fun inflateUserView(navView: NavigationView) {
        navView.removeHeaderView(navView.getHeaderView(0))
        HeaderNavigationUserBinding.bind(navView.inflateHeaderView(R.layout.header_navigation_user)).apply {
            val userData = loadUserData()
            user = userData
            userHeader.setOnClickListener { userData?.let(onUserClick) }
        }
    }

    private fun inflateLoginView(navView: NavigationView) {
        navView.removeHeaderView(navView.getHeaderView(0))
        navView.inflateHeaderView(R.layout.header_navigation_login).run {
            findViewById<Button>(R.id.header_navigation_login).setOnClickListener { onLoginButtonCallback(it) }
        }
    }

    private fun loadUserData(): UserData? {
        if (preferences.contains(UserData.KEY)) {
            return loginRepository.getUser()
        }
        return null
    }
}
