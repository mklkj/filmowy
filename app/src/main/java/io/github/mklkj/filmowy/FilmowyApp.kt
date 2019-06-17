package io.github.mklkj.filmowy

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.github.mklkj.filmowy.di.DaggerAppComponent

class FilmowyApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}
