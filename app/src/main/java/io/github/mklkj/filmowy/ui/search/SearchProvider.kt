package io.github.mklkj.filmowy.ui.search

import android.app.Activity
import android.app.SearchManager
import android.app.SearchManager.*
import android.database.Cursor
import android.database.MatrixCursor
import android.provider.BaseColumns
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.cursoradapter.widget.CursorAdapter
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.SearchResult
import io.github.mklkj.filmowy.api.repository.SearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SearchProvider @Inject constructor(private val searchRepository: SearchRepository) {

    private val disposable = CompositeDisposable()

    fun initSearch(menu: Menu, activity: Activity) {
        with(menu.findItem(R.id.search).actionView as SearchView) {
            val adapter = SearchSuggestionsAdapter(context)
            setSearchableInfo(context.getSystemService<SearchManager>()?.getSearchableInfo(activity.componentName))
            suggestionsAdapter = adapter
            setOnQueryTextListener(adapter)
        }
    }

    private fun SearchView.setOnQueryTextListener(adapter: CursorAdapter) {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = false
            override fun onQueryTextChange(query: String): Boolean {
                if (query.length < 2) return false
                performSearch(query, adapter)
                return true
            }
        })
    }

    private fun performSearch(query: String, adapter: CursorAdapter) {
        disposable.apply {
            clear()
            add(searchRepository.search(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.changeCursor(createCursorFromResult(it))
                }) { Timber.d(it) })
        }
    }

    private fun createCursorFromResult(list: List<SearchResult>): Cursor {
        return MatrixCursor(
            arrayOf(
                BaseColumns._ID,
                SUGGEST_COLUMN_ICON_1,
                SUGGEST_COLUMN_TEXT_1,
                SUGGEST_COLUMN_TEXT_2,
                SUGGEST_COLUMN_CONTENT_TYPE,
                SUGGEST_COLUMN_INTENT_DATA
            )
        ).also {
            list.forEachIndexed { index, result ->
                it.addRow(
                    arrayOf(
                        index,
                        "https://fwcdn.pl/" + when (result) {
                            is SearchResult.Film -> "fpo"
                            is SearchResult.Person -> "ppo"
                            is SearchResult.Channel -> "chan"
                            else -> ""
                        } + result.poster,
                        when (result) {
                            is SearchResult.Film -> result.originalTitle
                            else -> result.title
                        },
                        when (result) {
                            is SearchResult.Film -> result.year
                            else -> null
                        },
                        result.type.name,
                        result.toUrl()
                    )
                )
            }
        }
    }

    fun dispose() {
        disposable.dispose()
    }
}
