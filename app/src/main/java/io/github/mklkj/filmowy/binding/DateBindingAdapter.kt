package io.github.mklkj.filmowy.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class DateBindingAdapter {

    @BindingAdapter("android:text")
    fun TextView.formatDate(dateTime: LocalDateTime) {
        text = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}
