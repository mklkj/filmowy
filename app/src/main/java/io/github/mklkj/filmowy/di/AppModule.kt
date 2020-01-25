package io.github.mklkj.filmowy.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import io.github.mklkj.filmowy.FilmowyApp
import javax.inject.Singleton

@Module
internal class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: FilmowyApp): Context = app

    @Provides
    @Singleton
    fun provideSharedPref(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
}
