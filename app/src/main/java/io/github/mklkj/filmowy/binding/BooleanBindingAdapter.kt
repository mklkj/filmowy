package io.github.mklkj.filmowy.binding

import android.view.View
import androidx.databinding.BindingAdapter

class BooleanBindingAdapter {

    @BindingAdapter("android:visibility")
    fun View.setVisibility(value: Boolean) {
        visibility = if (value) View.VISIBLE else View.GONE
    }
}
