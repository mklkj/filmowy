package io.github.mklkj.filmowy.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.databinding.FragmentTvBinding
import io.github.mklkj.filmowy.R

class TvFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentTvBinding>(inflater, R.layout.fragment_tv, container, false).root
    }
}
