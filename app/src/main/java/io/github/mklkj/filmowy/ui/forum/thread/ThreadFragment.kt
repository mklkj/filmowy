package io.github.mklkj.filmowy.ui.forum.thread

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.databinding.FragmentThreadBinding

class ThreadFragment : DaggerFragment() {

    private val args: ThreadFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return FragmentThreadBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            text.text = args.toString()
        }.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.thread, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.thread_open_in_browser -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://m.filmweb.pl/${args.url.removePrefix("/")}")))
                true
            }
            else -> false
        }
    }
}
