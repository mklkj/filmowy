package io.github.mklkj.filmowy.binding.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class DateBindingAdapter @Inject constructor() {

    @BindingAdapter("android:text", "android:format")
    fun TextView.formatDateTime(dateTime: LocalDateTime, format: String) {
        text = dateTime.format(DateTimeFormatter.ofPattern(format))
    }

    @BindingAdapter("android:text", "android:format")
    fun TextView.formatDate(dateTime: LocalDate, format: String) {
        text = dateTime.format(DateTimeFormatter.ofPattern(format))
    }
}
