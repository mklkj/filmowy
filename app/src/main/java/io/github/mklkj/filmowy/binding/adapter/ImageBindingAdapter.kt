package io.github.mklkj.filmowy.binding.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import io.github.mklkj.filmowy.api.getNewsImageUrl
import io.github.mklkj.filmowy.api.getPersonFilmsImageUrl
import io.github.mklkj.filmowy.api.getPersonImageUrl
import io.github.mklkj.filmowy.api.getUserImageUrl

class ImageBindingAdapter(private val picasso: Picasso) {

    @BindingAdapter("android:newsImage", "android:imageWidth")
    fun ImageView.newsImage(url: String?, imageWidth: Int) {
        picasso
            .load(url?.getNewsImageUrl(imageWidth))
            .into(this)
    }

    @BindingAdapter("android:personImage", "android:imageWidth")
    fun ImageView.personImage(url: String?, imageWidth: Int) {
        picasso
            .load(url?.getPersonImageUrl(imageWidth))
            .into(this)
    }

    @BindingAdapter("android:userImage", "android:imageWidth")
    fun ImageView.userImage(url: String?, imageWidth: Int) {
        picasso
            .load(url?.getUserImageUrl(imageWidth))
            .into(this)
    }

    @BindingAdapter("android:filmImage", "android:imageWidth")
    fun ImageView.filmImage(url: String?, imageWidth: Int) {
        picasso
            .load(url?.getPersonFilmsImageUrl(imageWidth))
            .into(this)
    }
}
