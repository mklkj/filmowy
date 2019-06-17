package io.github.mklkj.filmowy

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.github.mklkj.filmowy.di.DaggerAppComponent
import timber.log.Timber

class FilmowyApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}
