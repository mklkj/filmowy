package io.github.mklkj.filmowy.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.databinding.FragmentMoviesBinding
import io.github.mklkj.filmowy.R

class MoviesFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentMoviesBinding>(inflater, R.layout.fragment_movies, container, false).root
    }
}
