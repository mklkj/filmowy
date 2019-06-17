package io.github.mklkj.filmowy.di

import android.content.Context
import dagger.Module
import dagger.Provides
import info.talacha.filmweb.api.FilmwebApi
import io.github.mklkj.filmowy.FilmowyApp
import javax.inject.Singleton

@Module
internal class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: FilmowyApp): Context = app

    @Singleton
    @Provides
    fun provideFilmwebApi() = FilmwebApi()
}
