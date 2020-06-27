package io.github.mklkj.filmowy.ui

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics.DENSITY_DEFAULT
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.elevation.ElevationOverlayProvider
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import io.github.mklkj.filmowy.NavGraphDirections
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.ui.login.NavigationLoginHelper
import io.github.mklkj.filmowy.utils.FilmwebLinkHandler
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentLifecycleLogger: FragmentLifecycleLogger

    @Inject
    lateinit var searchProvider: SearchProvider

    @Inject
    lateinit var navigationLoginHelper: NavigationLoginHelper

    @Inject
    lateinit var linkHandler: FilmwebLinkHandler

    private val navController by lazy { findNavController(R.id.navHostFragment) }

    private val navView by lazy { findViewById<NavigationView>(R.id.navView) }

    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawerLayout) }

    private val collapsingToolbarLayout by lazy { findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbarLayout) }

    private val toolbar by lazy { findViewById<MaterialToolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleLogger, true)
        setContentView(R.layout.activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.newsFragment,
                R.id.moviesFragment,
                R.id.seriesFragment,
                R.id.gamesFragment,
                R.id.rankingsFragment,
                R.id.cinemaFragment,
                R.id.tvFragment,
                R.id.forumFragment,
                R.id.myFragment
            ), drawerLayout
        )

        toolbar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(toolbar)
        collapsingToolbarLayout.isTitleEnabled = false
        collapsingToolbarLayout.setupWithNavController(toolbar, navController, appBarConfiguration)
        collapsingToolbarLayout.setBackgroundColor(
            ElevationOverlayProvider(this).compositeOverlayWithThemeSurfaceColorIfNeeded(4f * resources.displayMetrics.densityDpi / DENSITY_DEFAULT)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)
        navigationLoginHelper.apply {
            onUserClick = {
                navController.navigate(NavGraphDirections.actionGlobalProfileFragment(it))
                drawerLayout.closeDrawers()
            }
            onLoginButtonCallback = {
                navController.navigate(NavGraphDirections.actionGlobalLoginFragment())
                drawerLayout.closeDrawers()
            }
            updateNavigationHeader(navView)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.let { it.navigateUp(AppBarConfiguration(it.graph)) } || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.global, menu)
        searchProvider.initSearch(menu, this)
        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        linkHandler.parseIntent(intent, navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchProvider.dispose()
    }
}
