package io.github.mklkj.filmowy.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.pojo.Person
import io.github.mklkj.filmowy.api.pojo.SearchResult
import io.github.mklkj.filmowy.databinding.FragmentPersonBinding
import io.github.mklkj.filmowy.viewmodel.ViewModelFactory
import javax.inject.Inject

class PersonFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val args by navArgs<PersonFragmentArgs>()

    private val vm by lazy { ViewModelProviders.of(this, vmFactory).get(PersonViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentPersonBinding>(inflater, R.layout.fragment_person, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            person = (args.person as SearchResult.Person).run {
                Person(
                    name = title,
                    imagePath = poster,
                    votesCount = 0,
                    avgRate = .0,
                    personId = 0,
                    sex = 0,
                    height = null,
                    filmKnownFor = null,
                    hasBiography = false,
                    deathDate = null,
                    birthPlace = null,
                    birthDate = null,
                    realName = null
                )
            }
        }

        vm.getPersonInfo((args.person as SearchResult.Person).id.toLong()).observe(this, Observer { binding.person = it })

        return binding.root
    }
}
