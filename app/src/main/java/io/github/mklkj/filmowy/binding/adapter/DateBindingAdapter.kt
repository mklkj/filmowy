package io.github.mklkj.filmowy.binding.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class DateBindingAdapter {

    @BindingAdapter("android:text", "android:format")
    fun TextView.formatDate(dateTime: LocalDateTime, format: String) {
        text = dateTime.format(DateTimeFormatter.ofPattern(format))
    }
}
