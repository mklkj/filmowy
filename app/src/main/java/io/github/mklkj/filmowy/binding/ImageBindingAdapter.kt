package io.github.mklkj.filmowy.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import io.github.mklkj.filmowy.R

class ImageBindingAdapter(val picasso: Picasso) {

    @BindingAdapter("android:src")
    fun ImageView.loadImage(url: String) {
        picasso
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .into(this)
    }
}
