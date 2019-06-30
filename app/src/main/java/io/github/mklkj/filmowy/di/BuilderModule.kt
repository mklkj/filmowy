package io.github.mklkj.filmowy.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.mklkj.filmowy.ui.MainActivity
import io.github.mklkj.filmowy.ui.film.FilmFragment
import io.github.mklkj.filmowy.ui.person.PersonFragment

@Module
@Suppress("unused")
internal abstract class BuilderModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindFilmFragment(): FilmFragment

    @ContributesAndroidInjector
    abstract fun bindPersonFragment(): PersonFragment
}
