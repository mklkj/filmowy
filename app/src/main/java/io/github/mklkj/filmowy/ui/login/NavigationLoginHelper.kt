package io.github.mklkj.filmowy.ui.login

import android.content.SharedPreferences
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.getUserImageUrl
import io.github.mklkj.filmowy.api.pojo.UserData
import javax.inject.Inject

class NavigationLoginHelper @Inject constructor(
    private val preferences: SharedPreferences,
    private val picasso: Picasso
) {

    var onLoginButtonCallback: (View) -> Unit = {}

    fun updateNavigationHeader(navView: NavigationView) {
        loadUserData().let {
            if (it == null) inflateLoginView(navView)
            else inflateUserView(navView)
        }
    }

    private fun inflateUserView(navView: NavigationView) {
        navView.removeHeaderView(navView.getHeaderView(0))
        navView.inflateHeaderView(R.layout.header_navigation_user).run {
            loadUserData()?.let {
                findViewById<TextView>(R.id.header_navigation_email).text = it.nick
                findViewById<TextView>(R.id.header_navigation_name).text = it.name
                picasso.load(it.imagePath.getUserImageUrl(76)).into(findViewById<ImageView>(R.id.header_navigation_image))
            }
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
            return Gson().fromJson(preferences.getString(UserData.KEY, ""), UserData::class.java)
        }
        return null
    }
}
