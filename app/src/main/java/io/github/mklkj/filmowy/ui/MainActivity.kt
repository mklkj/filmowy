package io.github.mklkj.filmowy.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import dagger.android.support.DaggerAppCompatActivity
import io.github.mklkj.filmowy.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        toolbar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(toolbar)
        collapsingToolbarLayout.setupWithNavController(toolbar, navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, drawerLayout)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Toast.makeText(applicationContext, "$destination", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHostFragment.findNavController().let { it.navigateUp(AppBarConfiguration(it.graph)) } || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}
