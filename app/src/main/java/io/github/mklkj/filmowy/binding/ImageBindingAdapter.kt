package io.github.mklkj.filmowy.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.getNewsImageUrl

class ImageBindingAdapter(val picasso: Picasso) {

    @BindingAdapter("android:newsImage", "android:imageWidth")
    fun ImageView.newsImage(url: String, imageWidth: Int) {
        picasso
            .load(url.getNewsImageUrl(imageWidth))
            .placeholder(R.drawable.ic_placeholder)
            .into(this)
    }
}
