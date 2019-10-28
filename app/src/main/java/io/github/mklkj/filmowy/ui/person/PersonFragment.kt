package io.github.mklkj.filmowy.ui.person

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentPersonBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class PersonFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val vm: PersonViewModel by viewModels { vmFactory }

    private val args: PersonFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val binding = DataBindingUtil.inflate<FragmentPersonBinding>(inflater, R.layout.fragment_person, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            person = args.person
        }

        vm.getPersonInfo(args.person.personId).observe(viewLifecycleOwner, Observer { binding.person = it })

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
