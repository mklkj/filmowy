package io.github.mklkj.filmowy.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.*

@BindingAdapter("android:image")
fun ImageView.image(url: String?) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_placeholder_bg)
        .centerCrop()
        .into(this)
}

@BindingAdapter("android:newsImage", "android:imageWidth")
fun ImageView.newsImage(url: String?, imageWidth: Int) {
    Glide.with(this)
        .load(url?.getNewsImageUrl(imageWidth))
        .into(this)
}

@BindingAdapter("android:personImage", "android:imageWidth")
fun ImageView.personImage(url: String?, imageWidth: Int) {
    Glide.with(this)
        .load(url?.getPersonImageUrl(imageWidth))
        .into(this)
}

@BindingAdapter("android:userImage", "android:imageWidth")
fun ImageView.userImage(url: String?, imageWidth: Int) {
    Glide.with(this)
        .load(url?.getUserImageUrl(imageWidth))
        .into(this)
}

@BindingAdapter("android:episodeImage", "android:imageWidth")
fun ImageView.episodeImage(url: String?, imageWidth: Int) {
    Glide.with(this)
        .load(url?.getFilmImageUrl(imageWidth))
        .into(this)
}

@BindingAdapter("android:filmImage", "android:imageWidth")
fun ImageView.filmImage(url: String?, imageWidth: Int) {
    Glide.with(this)
        .load(url?.getPersonFilmsImageUrl(imageWidth))
        .into(this)
}
