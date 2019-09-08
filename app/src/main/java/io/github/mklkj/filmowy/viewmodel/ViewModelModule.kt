package io.github.mklkj.filmowy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import io.github.mklkj.filmowy.ui.article.ArticleViewModel
import io.github.mklkj.filmowy.ui.film.FilmViewModel
import io.github.mklkj.filmowy.ui.film.episodes.EpisodesViewModel
import io.github.mklkj.filmowy.ui.login.LoginViewModel
import io.github.mklkj.filmowy.ui.my.MyViewModel
import io.github.mklkj.filmowy.ui.news.NewsViewModel
import io.github.mklkj.filmowy.ui.person.PersonViewModel
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
    @ClassKey(PersonViewModel::class)
    abstract fun bindPersonViewModel(personViewModel: PersonViewModel): ViewModel

    @Binds
    @IntoMap
    @ClassKey(FilmViewModel::class)
    abstract fun bindFilmViewModel(filmViewModel: FilmViewModel): ViewModel

    @Binds
    @IntoMap
    @ClassKey(EpisodesViewModel::class)
    abstract fun bindEpisodesViewModel(episodesViewModel: EpisodesViewModel): ViewModel

    @Binds
    @IntoMap
    @ClassKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ClassKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ClassKey(MyViewModel::class)
    abstract fun bindMyViewModel(myViewModel: MyViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
