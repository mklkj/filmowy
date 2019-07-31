package io.github.mklkj.filmowy.ui.cinema

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.databinding.FragmentCinemaBinding
import io.github.mklkj.filmowy.R

class CinemaFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentCinemaBinding>(inflater, R.layout.fragment_cinema, container, false).root
    }
}
