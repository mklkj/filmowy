package io.github.mklkj.filmowy.ui.adapter

import android.content.Intent
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import kotlin.math.round

@BindingAdapter("android:duration")
fun TextView.setDuration(duration: Int) {
    val hours = duration / 60
    val leadingMinutes = duration % 60

    text = when {
        hours == 0 -> "$duration min."
        leadingMinutes == 0 -> "$hours godz."
        else -> "$hours godz. $leadingMinutes min."
    }
}

@BindingAdapter("android:avg")
fun TextView.setAverage(avg: Double) {
    text = (round(avg * 100) / 100).toString().replace(".", ",")
}

@BindingAdapter("android:netflixUrl")
fun TextView.setNetflixUrl(link: String?) {
    setLink(link?.toUri()?.getQueryParameter("url"))
}

@BindingAdapter("android:hboUrl")
fun TextView.setHboUrl(link: String?) {
    setLink(link?.split("tfua=?")?.last())
}

private fun TextView.setLink(link: String?) {
    link?.let { url ->
        if (url.isBlank()) return
        movementMethod = LinkMovementMethod.getInstance()
        setOnClickListener {
            context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
        }
    }
}
