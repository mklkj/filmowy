package io.github.mklkj.filmowy.ui.forum.thread

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.base.BaseFragment
import io.github.mklkj.filmowy.databinding.FragmentThreadBinding

@AndroidEntryPoint
class ThreadFragment : BaseFragment<FragmentThreadBinding>(R.layout.fragment_thread) {

    private val args: ThreadFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        binding.text.text = args.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.thread, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = if (item.itemId == R.id.thread_open_in_browser) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://m.filmweb.pl/${args.url.removePrefix("/")}")))
        true
    } else false
}
