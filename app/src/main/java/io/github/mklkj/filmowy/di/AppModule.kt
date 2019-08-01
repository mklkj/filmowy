package io.github.mklkj.filmowy.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import io.github.mklkj.filmowy.BuildConfig
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

    @Provides
    @Singleton
    fun providePicasso(context: Context): Picasso = Picasso.Builder(context)
        .loggingEnabled(BuildConfig.DEBUG)
        .build()
}
