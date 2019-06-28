package io.github.mklkj.filmowy.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.ActivityMainBinding
import io.github.mklkj.filmowy.di.ViewModelFactory
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            mainViewModel = viewModel
            lifecycleOwner = this@MainActivity
        }

        viewModel.loadFullFilmInfo()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:imageUrl")
        fun setImageUrl(view: ImageView, url: String?) {
            if (url != null) { Picasso.get().load(url).into(view) }
        }
    }
}
