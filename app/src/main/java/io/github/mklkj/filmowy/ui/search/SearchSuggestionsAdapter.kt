package io.github.mklkj.filmowy.ui.search

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cursoradapter.widget.CursorAdapter
import androidx.databinding.DataBindingUtil
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.ItemSearchSuggestionBinding

class SearchSuggestionsAdapter(context: Context) : CursorAdapter(context, null, FLAG_REGISTER_CONTENT_OBSERVER) {

    override fun newView(context: Context, cursor: Cursor?, parent: ViewGroup?) =
        DataBindingUtil.inflate<ItemSearchSuggestionBinding>(LayoutInflater.from(context), R.layout.item_search_suggestion, parent, false).root

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        with(DataBindingUtil.findBinding<ItemSearchSuggestionBinding>(view)!!) {
            cover = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_ICON_1))
            title = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
            year = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_2))
            type = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_CONTENT_TYPE)).toLowerCase()
            executePendingBindings()
        }
    }
}
