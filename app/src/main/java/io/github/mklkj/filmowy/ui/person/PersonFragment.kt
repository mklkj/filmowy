package io.github.mklkj.filmowy.ui.person

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentPersonBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class PersonFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val args by navArgs<PersonFragmentArgs>()

    private val vm by lazy { ViewModelProviders.of(this, vmFactory).get(PersonViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val binding = DataBindingUtil.inflate<FragmentPersonBinding>(inflater, R.layout.fragment_person, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            person = args.person
        }

        vm.getPersonInfo(args.person.personId).observe(this, Observer { binding.person = it })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.person, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.person_open_in_browser -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://m.filmweb.pl/person/-${args.person.personId}")))
                true
            }
            else -> false
        }
    }
}
