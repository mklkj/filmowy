package io.github.mklkj.filmowy.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import io.github.mklkj.filmowy.FilmowyApp
import io.github.mklkj.filmowy.api.ApiModule
import io.github.mklkj.filmowy.viewmodel.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ApiModule::class,
        BuilderModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: FilmowyApp): Builder

        fun build(): AppComponent
    }
}
