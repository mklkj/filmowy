package io.github.mklkj.filmowy.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.github.mklkj.filmowy.FilmowyApp
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        BuilderModule::class
    ]
)
interface AppComponent : AndroidInjector<FilmowyApp> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<FilmowyApp>
}
