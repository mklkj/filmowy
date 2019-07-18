package io.github.mklkj.filmowy.ui.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentFilmBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class FilmFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vm = ViewModelProviders.of(this, viewModelFactory).get(FilmViewModel::class.java)
        val binding =  DataBindingUtil.inflate<FragmentFilmBinding>(inflater, R.layout.fragment_film, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
        }

        vm.film.observe(viewLifecycleOwner, Observer { binding.film = it })

        return binding.root
    }
}
