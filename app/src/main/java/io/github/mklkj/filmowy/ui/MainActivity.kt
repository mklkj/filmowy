package io.github.mklkj.filmowy.ui

import android.app.SearchManager
import android.app.SearchManager.*
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.Bundle
import android.provider.BaseColumns
import android.util.DisplayMetrics.DENSITY_DEFAULT
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.elevation.ElevationOverlayProvider
import com.google.android.material.navigation.NavigationView
import dagger.android.support.DaggerAppCompatActivity
import io.github.mklkj.filmowy.NavGraphDirections
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.api.pojo.Person
import io.github.mklkj.filmowy.api.pojo.SearchResult
import io.github.mklkj.filmowy.api.pojo.SearchResult.Type.*
import io.github.mklkj.filmowy.api.repository.SearchRepository
import io.github.mklkj.filmowy.ui.login.NavigationLoginHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var fragmentLifecycleLogger: FragmentLifecycleLogger

    @Inject
    lateinit var searchRepository: SearchRepository

    @Inject
    lateinit var navigationLoginHelper: NavigationLoginHelper

    private val disposable = CompositeDisposable()

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

        val adapter = SimpleCursorAdapter(
            this, android.R.layout.simple_list_item_1, null,
            arrayOf(SUGGEST_COLUMN_TEXT_1), intArrayOf(android.R.id.text1), CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

        (menu.findItem(R.id.search).actionView as SearchView).run {
            setSearchableInfo((getSystemService(Context.SEARCH_SERVICE) as SearchManager).getSearchableInfo(componentName))
            suggestionsAdapter = adapter
            setOnQueryTextListener(adapter)
        }

        return true
    }

    private fun SearchView.setOnQueryTextListener(adapter: CursorAdapter) {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = false
            override fun onQueryTextChange(query: String): Boolean {
                if (query.length < 2) return false
                disposable.apply {
                    clear()
                    add(searchRepository.search(query)
                        .subscribeOn(Schedulers.io())
                        .map { list -> list.map { query to it } }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            adapter.swapCursor(createCursorFromResult(it))
                        }) { Timber.d(it) })
                }
                return true
            }
        })
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        when (intent.action) {
            Intent.ACTION_SEARCH -> if (intent.data != null) {
                intent.data!!.lastPathSegment!!.toLong().also {
                    when (SearchResult.Type.getByName(intent.data!!.pathSegments[0])) {
                        FILM, SERIES -> navController.navigate(NavGraphDirections.actionGlobalFilmFragment(Film.get(it)))
                        GAME -> TODO()
                        PERSON -> navController.navigate(NavGraphDirections.actionGlobalPersonFragment(Person.get(it)))
                        CHANNEL -> TODO()
                        CINEMA -> TODO()
                    }
                }
            } else {
                intent.getStringExtra(QUERY)?.let { navController.navigate(NavGraphDirections.actionGlobalSearchFragment(it)) }
            }
            Intent.ACTION_VIEW -> if (intent.data != null) {
                intent.data!!.pathSegments.also {
                    when(it[0]) {
                        "news" -> navController.navigate(NavGraphDirections.actionGlobalArticleFragment(News.get(it.last().split("-").last().toLong()), -1))
                        "film", "serial" -> navController.navigate(NavGraphDirections.actionGlobalFilmFragment(Film.get(it.last().split("-").last().toLong())))
                        "person" -> navController.navigate(NavGraphDirections.actionGlobalPersonFragment(Person.get(it.last().split("-").last().toLong())))
                        else -> startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse("https://m.filmweb.pl/${intent.data?.path}") })
                    }
                }
            }
        }
    }

    private fun createCursorFromResult(list: List<Pair<String, SearchResult>>): Cursor {
        return MatrixCursor(
            arrayOf(
                BaseColumns._ID,
                SUGGEST_COLUMN_TEXT_1,
                SUGGEST_COLUMN_INTENT_DATA_ID,
                SUGGEST_COLUMN_INTENT_DATA
            )
        ).apply {
            list.mapIndexed { index, (_, result) ->
                addRow(arrayOf(index, result.title, result.id, result.type))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
