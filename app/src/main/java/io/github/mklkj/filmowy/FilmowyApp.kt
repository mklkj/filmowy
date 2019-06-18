package io.github.mklkj.filmowy

import androidx.appcompat.app.AppCompatDelegate
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.github.mklkj.filmowy.di.DaggerAppComponent
import timber.log.Timber

class FilmowyApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}
