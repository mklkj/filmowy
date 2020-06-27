package io.github.mklkj.filmowy.ui.adapter

import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.databinding.BindingAdapter
import io.github.mklkj.filmowy.ui.HtmlImageGetter

@BindingAdapter("android:visibility")
fun View.setVisibility(value: Boolean) {
    visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("android:text")
fun TextView.setDoubleAsText(content: Double) {
    text = content.toString().replace(".", ",")
}

@BindingAdapter("android:number")
fun TextView.setIntAsText(content: Int) {
    text = content.toString()
}

@BindingAdapter("android:text")
fun TextView.setListAsText(list: List<String>?) {
    text = list?.joinToString(", ")
}

@Suppress("DEPRECATION")
@BindingAdapter("android:text", "android:html")
fun TextView.setHtml(content: String?, html: String?) {
    movementMethod = LinkMovementMethod.getInstance()
    text = when {
        html?.isEmpty() == true -> content
        else -> HtmlCompat.fromHtml(html.orEmpty(), FROM_HTML_MODE_COMPACT, HtmlImageGetter(context).apply { textView = this@setHtml }, null)
    }
}

@BindingAdapter("android:text")
fun TextView.setLongAsText(long: Long?) {
    text = long.toString()
}
