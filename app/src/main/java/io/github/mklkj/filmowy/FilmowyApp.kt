package io.github.mklkj.filmowy

import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.github.mklkj.filmowy.binding.BindingModule
import io.github.mklkj.filmowy.binding.DaggerBindingComponent
import io.github.mklkj.filmowy.di.DaggerAppComponent
import timber.log.Timber

class FilmowyApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AndroidThreeTen.init(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()

        val bindingComponent = DaggerBindingComponent.builder()
            .bindAppComponent(appComponent)
            .bindingModule(BindingModule)
            .picasso(Picasso.Builder(applicationContext)
                .loggingEnabled(BuildConfig.DEBUG)
                .build())
            .build()

        DataBindingUtil.setDefaultComponent(bindingComponent)

        return appComponent
    }
}
