package io.github.mklkj.filmowy.binding.adapter

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.github.mklkj.filmowy.ui.HtmlImageGetter
import javax.inject.Inject

class AttributeHelperBindingAdapter @Inject constructor(private val htmlImageGetter: HtmlImageGetter) {

    @BindingAdapter("android:visibility")
    fun View.setVisibility(value: Boolean) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

    @BindingAdapter("android:text")
    fun TextView.setDoubleAsText(content: Double) {
        text = content.toString()
    }

    @BindingAdapter("android:text")
    fun TextView.setListAsText(list: List<String>?) {
        text = list?.joinToString(", ")
    }

    @Suppress("DEPRECATION")
    @BindingAdapter("android:text", "android:html")
    fun TextView.setHtml(content: String, html: String) {
        movementMethod = LinkMovementMethod.getInstance()
        text = when {
            html.isEmpty() -> content
            else -> if (VERSION.SDK_INT >= VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT, htmlImageGetter.apply { textView = this@setHtml }, null)
            } else {
                Html.fromHtml(html, htmlImageGetter.apply { textView = this@setHtml }, null)
            }
        }
    }

    @BindingAdapter("android:text")
    fun TextView.setLongAsText(long: Long?) {
        text = long.toString()
    }
}
