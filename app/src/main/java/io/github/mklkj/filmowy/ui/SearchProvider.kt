package io.github.mklkj.filmowy.ui

import android.app.Activity
import android.app.SearchManager
import android.app.SearchManager.SUGGEST_COLUMN_INTENT_DATA
import android.app.SearchManager.SUGGEST_COLUMN_TEXT_1
import android.database.Cursor
import android.database.MatrixCursor
import android.provider.BaseColumns
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
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
        val adapter = SimpleCursorAdapter(
            activity,
            android.R.layout.simple_list_item_1,
            null,
            arrayOf(SUGGEST_COLUMN_TEXT_1),
            intArrayOf(android.R.id.text1),
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

        (menu.findItem(R.id.search).actionView as SearchView).run {
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
                .map { list -> list.map { query to it } }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.changeCursor(createCursorFromResult(it))
                }) { Timber.d(it) })
        }
    }

    private fun createCursorFromResult(list: List<Pair<String, SearchResult>>): Cursor {
        return MatrixCursor(
            arrayOf(
                BaseColumns._ID,
                SUGGEST_COLUMN_TEXT_1,
                SUGGEST_COLUMN_INTENT_DATA
            )
        ).apply {
            list.mapIndexed { index, (_, result) ->
                addRow(arrayOf(index, result.title, result.toUrl()))
            }
        }
    }

    fun dispose() {
        disposable.dispose()
    }
}
