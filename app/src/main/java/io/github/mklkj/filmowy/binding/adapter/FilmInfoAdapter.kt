package io.github.mklkj.filmowy.binding.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import javax.inject.Inject
import kotlin.math.round

class FilmInfoAdapter @Inject constructor() {

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
        text = (round(avg * 100) / 100).toString()
    }
}