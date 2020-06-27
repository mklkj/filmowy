package io.github.mklkj.filmowy.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.pojo.Person
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentSearchBinding
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override val viewModel: SearchViewModel by viewModels()

    @Inject
    lateinit var dataAdapter: SearchResultsAdapter

    private val args: SearchFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getSearchResults(args.query).observe(this, Observer {
            dataAdapter.items = it
            dataAdapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initializeAdapter()
    }

    private fun initializeAdapter() {
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }

        dataAdapter.openFilmCallback = {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToFilmFragment(Film.get(it.id.toLong(), it.title, it.poster))
            )
        }

        dataAdapter.openPersonCallback = {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToPersonFragment(Person.get(it.id.toLong(), it.title, it.poster))
            )
        }
    }
}
