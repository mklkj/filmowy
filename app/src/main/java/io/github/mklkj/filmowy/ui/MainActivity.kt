package io.github.mklkj.filmowy.ui

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.FilmRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var filmRepository: FilmRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            filmRepository.getFilmPersonsLead(771634, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    container.text = it.toString()
                }) {
                    Timber.e(it)
                    container.text = it.localizedMessage
                }
        }
    }
}
