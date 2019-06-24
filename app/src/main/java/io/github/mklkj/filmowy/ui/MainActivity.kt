package io.github.mklkj.filmowy.ui

import android.annotation.SuppressLint
import android.os.Bundle
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.getPersonFilmsImageUrl
import io.github.mklkj.filmowy.api.repository.PersonRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var personRepository: PersonRepository

    @Inject
    lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var i = 0
        reloadImage(i)
        fab.setOnClickListener { reloadImage(++i) }
    }

    @SuppressLint("SetTextI18n")
    private fun reloadImage(index: Int) {
        disposable.clear()
        disposable.add(personRepository.getPersonFilms(48152, 1, 6, 0, 5)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.getOrNull(index)?.run {
                    picasso.load(filmImagePath?.getPersonFilmsImageUrl(200)).into(image)
                    container.text = "$index: $filmTitle"
                }
            }) {
                Timber.e(it)
                container.text = it.localizedMessage
            })
    }

    public override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
