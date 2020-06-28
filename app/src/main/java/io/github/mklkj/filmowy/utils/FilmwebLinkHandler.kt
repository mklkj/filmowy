package io.github.mklkj.filmowy.utils

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import dagger.hilt.android.qualifiers.ActivityContext
import io.github.mklkj.filmowy.NavGraphDirections
import io.github.mklkj.filmowy.NavGraphDirections.Companion.actionGlobalFilmFragment
import io.github.mklkj.filmowy.NavGraphDirections.Companion.actionGlobalPersonFragment
import io.github.mklkj.filmowy.NavGraphDirections.Companion.actionGlobalSearchFragment
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
            when (intent.data!!.pathSegments[0].toUpperCase()) {
                SearchResult.Type.FILM.name, SearchResult.Type.SERIAL.name -> navController.navigate(actionGlobalFilmFragment(intent.dataString.orEmpty()))
                SearchResult.Type.VIDEOGAME.name -> TODO()
                SearchResult.Type.PERSON.name -> navController.navigate(actionGlobalPersonFragment(Person.get(intent.data!!.lastPathSegment!!.split("-").last().toLong())))
            }
            when(intent.data?.getQueryParameter("entityName")?.toUpperCase()) {
                SearchResult.Type.CHANNEL.name -> TODO()
                SearchResult.Type.CINEMA.name -> TODO()
            }
        } else {
            intent.getStringExtra(SearchManager.QUERY)?.let { navController.navigate(actionGlobalSearchFragment(it)) }
        }
    }

    private fun parseViewIntent(intent: Intent, navController: NavController) {
        if (intent.data == null) return

        intent.data!!.pathSegments.also {
            try {
                val id = it.last().split("-").last().toLong()
                when (it[0]) {
                    "news" -> navController.navigate(NavGraphDirections.actionGlobalArticleFragment(News.get(id), -1))
                    "film", "serial" -> navController.navigate(actionGlobalFilmFragment(intent.dataString.orEmpty()))
                    "person" -> navController.navigate(actionGlobalPersonFragment(Person.get(id)))
                    else -> handleFallback(intent.data)
                }
            } catch (e: NumberFormatException) {
                handleFallback(intent.data)
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
