package io.github.mklkj.filmowy.ui.person

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentPersonBinding

class PersonFragment : BaseFragment<FragmentPersonBinding>(R.layout.fragment_person) {

    private val vm: PersonViewModel by viewModels { vmFactory }

    private val args: PersonFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        binding.person = args.person
        vm.getPersonInfo(args.person.personId).observe(viewLifecycleOwner, Observer { binding.person = it })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.person, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = if (item.itemId == R.id.person_open_in_browser) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://m.filmweb.pl/person/-${args.person.personId}")))
        true
    } else false
}
