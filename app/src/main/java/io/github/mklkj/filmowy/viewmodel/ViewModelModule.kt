package io.github.mklkj.filmowy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import io.github.mklkj.filmowy.ui.film.FilmViewModel
import io.github.mklkj.filmowy.ui.news.NewsViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ClassKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(filmViewModel: NewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ClassKey(FilmViewModel::class)
    abstract fun bindFilmViewModel(filmViewModel: FilmViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
