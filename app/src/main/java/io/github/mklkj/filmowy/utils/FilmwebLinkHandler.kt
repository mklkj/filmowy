package io.github.mklkj.filmowy.utils

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import dagger.hilt.android.qualifiers.ActivityContext
import io.github.mklkj.filmowy.NavGraphDirections
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.api.pojo.Person
import io.github.mklkj.filmowy.api.pojo.SearchResult
import timber.log.Timber
import javax.inject.Inject

class FilmwebLinkHandler @Inject constructor(@ActivityContext private val context: Context) {

    fun parseIntent(intent: Intent, navController: NavController) {
        when (intent.action) {
            Intent.ACTION_SEARCH -> parseSearchIntent(intent, navController)
            Intent.ACTION_VIEW -> parseViewIntent(intent, navController)
        }
    }

    private fun parseSearchIntent(intent: Intent, navController: NavController) {
        if (intent.data != null) {
            intent.data!!.lastPathSegment!!.toLong().also {
                when (SearchResult.Type.getByName(intent.data!!.pathSegments[0])) {
                    SearchResult.Type.FILM, SearchResult.Type.SERIES -> navController.navigate(NavGraphDirections.actionGlobalFilmFragment(Film.get(it)))
                    SearchResult.Type.GAME -> TODO()
                    SearchResult.Type.PERSON -> navController.navigate(NavGraphDirections.actionGlobalPersonFragment(Person.get(it)))
                    SearchResult.Type.CHANNEL -> TODO()
                    SearchResult.Type.CINEMA -> TODO()
                }
            }
        } else {
            intent.getStringExtra(SearchManager.QUERY)?.let { navController.navigate(NavGraphDirections.actionGlobalSearchFragment(it)) }
        }
    }

    private fun parseViewIntent(intent: Intent, navController: NavController) {
        if (intent.data != null) {
            intent.data!!.pathSegments.also {
                try {
                    val id = it.last().split("-").last().toLong()
                    when (it[0]) {
                        "news" -> navController.navigate(NavGraphDirections.actionGlobalArticleFragment(News.get(id), -1))
                        "film", "serial" -> navController.navigate(NavGraphDirections.actionGlobalFilmFragment(Film.get(id)))
                        "person" -> navController.navigate(NavGraphDirections.actionGlobalPersonFragment(Person.get(id)))
                        else -> handleFallback(intent.data)
                    }
                } catch (e: NumberFormatException) {
                    handleFallback(intent.data)
                }
            }
        }
    }

    private fun handleFallback(uri: Uri?) {
        Timber.d("Can't extract id from ${uri?.path}, fallback to browser")
        context.startActivity(Intent(Intent.ACTION_VIEW).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.parse("https://m.filmweb.pl/${uri?.path?.removePrefix("/")}")
        })
    }
}
