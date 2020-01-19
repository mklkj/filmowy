package io.github.mklkj.filmowy.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.mklkj.filmowy.ui.MainActivity
import io.github.mklkj.filmowy.ui.article.ArticleFragment
import io.github.mklkj.filmowy.ui.cinema.CinemaFragment
import io.github.mklkj.filmowy.ui.film.FilmFragment
import io.github.mklkj.filmowy.ui.film.episodes.EpisodesFragment
import io.github.mklkj.filmowy.ui.film.episodes.tab.EpisodesTabFragment
import io.github.mklkj.filmowy.ui.forum.ForumFragment
import io.github.mklkj.filmowy.ui.forum.ForumTabFragment
import io.github.mklkj.filmowy.ui.forum.thread.ThreadFragment
import io.github.mklkj.filmowy.ui.games.GamesFragment
import io.github.mklkj.filmowy.ui.login.LoginFragment
import io.github.mklkj.filmowy.ui.movies.MoviesFragment
import io.github.mklkj.filmowy.ui.my.MyFragment
import io.github.mklkj.filmowy.ui.news.NewsFragment
import io.github.mklkj.filmowy.ui.person.PersonFragment
import io.github.mklkj.filmowy.ui.profile.ProfileFragment
import io.github.mklkj.filmowy.ui.rankings.RankingsFragment
import io.github.mklkj.filmowy.ui.search.SearchFragment
import io.github.mklkj.filmowy.ui.series.SeriesFragment
import io.github.mklkj.filmowy.ui.tv.TvFragment

@Module
@Suppress("unused")
internal abstract class BuilderModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindArticleFragment(): ArticleFragment

    @ContributesAndroidInjector
    abstract fun bindCinemaFragment(): CinemaFragment

    @ContributesAndroidInjector
    abstract fun bindGamesFragment(): GamesFragment

    @ContributesAndroidInjector
    abstract fun bindLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun bindFilmFragment(): FilmFragment

    @ContributesAndroidInjector
    abstract fun bindForumFragment(): ForumFragment

    @ContributesAndroidInjector
    abstract fun bindForumTabFragment(): ForumTabFragment

    @ContributesAndroidInjector
    abstract fun bindForumThreadFragment(): ThreadFragment

    @ContributesAndroidInjector
    abstract fun bindEpisodesFragment(): EpisodesFragment

    @ContributesAndroidInjector
    abstract fun bindEpisodesTabFragment(): EpisodesTabFragment

    @ContributesAndroidInjector
    abstract fun bindMoviesFragment(): MoviesFragment

    @ContributesAndroidInjector
    abstract fun bindNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    abstract fun bindPersonFragment(): PersonFragment

    @ContributesAndroidInjector
    abstract fun bindProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun bindRankingsFragment(): RankingsFragment

    @ContributesAndroidInjector
    abstract fun bindSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun bindSeriesFragment(): SeriesFragment

    @ContributesAndroidInjector
    abstract fun bindTvFragment(): TvFragment

    @ContributesAndroidInjector
    abstract fun bindMyFragment(): MyFragment
}
