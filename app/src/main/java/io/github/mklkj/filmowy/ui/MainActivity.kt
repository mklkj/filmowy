package io.github.mklkj.filmowy.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.navigation.NavigationView
import dagger.android.support.DaggerAppCompatActivity
import io.github.mklkj.filmowy.R

class MainActivity : DaggerAppCompatActivity() {

    private val navController by lazy { findNavController(R.id.navHostFragment) }

    private val navView by lazy { findViewById<NavigationView>(R.id.navView) }

    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawerLayout) }

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    private val collapsingToolbarLayout by lazy { findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbarLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.newsFragment,
            R.id.filmFragment,
            R.id.personFragment
        ), drawerLayout)

        toolbar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(toolbar)
        navView.setupWithNavController(navController)
        collapsingToolbarLayout.setupWithNavController(toolbar, navController, appBarConfiguration)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.let { it.navigateUp(AppBarConfiguration(it.graph)) } || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}
