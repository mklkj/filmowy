package io.github.mklkj.filmowy.ui.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentSeriesBinding

class SeriesFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentSeriesBinding>(inflater, R.layout.fragment_series, container, false).root
    }
}
