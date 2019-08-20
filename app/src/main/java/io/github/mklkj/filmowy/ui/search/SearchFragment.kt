package io.github.mklkj.filmowy.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.pojo.Person
import io.github.mklkj.filmowy.databinding.FragmentSearchBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val vm: SearchViewModel by viewModels { vmFactory }

    @Inject
    lateinit var dataAdapter: SearchResultsAdapter

    private val args: SearchFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.getSearchResults(args.query).observe(this, Observer {
            dataAdapter.items = it
            dataAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentSearchBinding>(inflater, R.layout.fragment_search, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
            initializeAdapter(this)
        }.root
    }

    private fun initializeAdapter(binding: FragmentSearchBinding) {
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }

        dataAdapter.openFilmCallback = {
            binding.root.findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToFilmFragment(Film.get(it.id.toLong(), it.title, it.poster))
            )
        }

        dataAdapter.openPersonCallback = {
            binding.root.findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToPersonFragment(Person.get(it.id.toLong(), it.title, it.poster))
            )
        }
    }
}
