package io.github.mklkj.filmowy.binding.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.*

class ImageBindingAdapter(private val picasso: Picasso) {

    @BindingAdapter("android:image")
    fun ImageView.image(url: String?) {
        picasso
            .load(url?.ifBlank { "https://3.fwcdn.pl/gf/iri/placeholders/d.b60b5378472c41cbf07bacc3ac17987a.svg" })
            .placeholder(R.drawable.ic_placeholder_bg)
            .into(this)
    }

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

    @BindingAdapter("android:episodeImage", "android:imageWidth")
    fun ImageView.episodeImage(url: String?, imageWidth: Int) {
        picasso
            .load(url?.getFilmImageUrl(imageWidth))
            .into(this)
    }

    @BindingAdapter("android:filmImage", "android:imageWidth")
    fun ImageView.filmImage(url: String?, imageWidth: Int) {
        picasso
            .load(url?.getPersonFilmsImageUrl(imageWidth))
            .into(this)
    }
}
