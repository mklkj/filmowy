package io.github.mklkj.filmowy

import androidx.databinding.DataBindingUtil
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.github.mklkj.filmowy.binding.BindingModule
import io.github.mklkj.filmowy.binding.DaggerBindingComponent
import io.github.mklkj.filmowy.di.AppComponent
import io.github.mklkj.filmowy.di.DaggerAppComponent
import timber.log.Timber

class FilmowyApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AndroidThreeTen.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build().also {
            initializeDataBindingComponent(it)
        }
    }

    private fun initializeDataBindingComponent(appComponent: AppComponent) {
        DataBindingUtil.setDefaultComponent(
            DaggerBindingComponent.builder()
                .bindAppComponent(appComponent)
                .bindingModule(BindingModule)
                .application(this)
                .build()
        )
    }
}
