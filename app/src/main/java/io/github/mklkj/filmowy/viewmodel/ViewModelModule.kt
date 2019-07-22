package io.github.mklkj.filmowy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import io.github.mklkj.filmowy.ui.article.ArticleViewModel
import io.github.mklkj.filmowy.ui.film.FilmViewModel
import io.github.mklkj.filmowy.ui.news.NewsViewModel
import io.github.mklkj.filmowy.ui.search.SearchViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ClassKey(ArticleViewModel::class)
    abstract fun bindArticleViewModel(articleViewModel: ArticleViewModel): ViewModel

    @Binds
    @IntoMap
    @ClassKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(filmViewModel: NewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ClassKey(FilmViewModel::class)
    abstract fun bindFilmViewModel(filmViewModel: FilmViewModel): ViewModel

    @Binds
    @IntoMap
    @ClassKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
