package io.github.mklkj.filmowy.binding.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

class AttributeHelperBindingAdapter {

    @BindingAdapter("android:visibility")
    fun View.setVisibility(value: Boolean) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

    @BindingAdapter("android:text")
    fun TextView.setListAsText(list: List<String>?) {
        text = list?.joinToString { ", " }
    }
}
