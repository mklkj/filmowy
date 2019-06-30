package io.github.mklkj.filmowy.ui.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentFilmBinding
import io.github.mklkj.filmowy.di.ViewModelFactory
import javax.inject.Inject

class FilmFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var vm: FilmViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vm = ViewModelProviders.of(this, viewModelFactory).get(FilmViewModel::class.java)

        return DataBindingUtil.inflate<FragmentFilmBinding>(inflater, R.layout.fragment_film, container, false).apply {
            lifecycleOwner = this@FilmFragment
            viewModel = vm.apply {
                loadFullFilmInfo()
            }
        }.root
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:imageUrl")
        fun setImageUrl(view: ImageView, url: String?) {
            url?.let { Picasso.get().load(it).into(view) }
        }
    }
}
