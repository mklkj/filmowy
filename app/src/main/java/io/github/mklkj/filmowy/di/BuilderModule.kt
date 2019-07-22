package io.github.mklkj.filmowy.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.mklkj.filmowy.ui.MainActivity
import io.github.mklkj.filmowy.ui.article.ArticleFragment
import io.github.mklkj.filmowy.ui.film.FilmFragment
import io.github.mklkj.filmowy.ui.news.NewsFragment
import io.github.mklkj.filmowy.ui.person.PersonFragment
import io.github.mklkj.filmowy.ui.search.SearchFragment

@Module
@Suppress("unused")
internal abstract class BuilderModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindArticleFragment(): ArticleFragment

    @ContributesAndroidInjector
    abstract fun bindNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    abstract fun bindFilmFragment(): FilmFragment

    @ContributesAndroidInjector
    abstract fun bindPersonFragment(): PersonFragment

    @ContributesAndroidInjector
    abstract fun bindSearchFragment(): SearchFragment
}
