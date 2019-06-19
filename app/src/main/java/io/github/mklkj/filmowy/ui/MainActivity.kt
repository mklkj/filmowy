package io.github.mklkj.filmowy.ui

import android.annotation.SuppressLint
import android.os.Bundle
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import io.github.mklkj.filmowy.R
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.github.mklkj.filmowy.api.getImageUrl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var filmRepository: FilmRepository

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
        disposable.add(filmRepository.getFilmImages(771634, 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                picasso.load(it[index].imagePath.getImageUrl(500)).into(image)
                container.text = "$index: " + it[index].persons?.joinToString { it.personName }
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
