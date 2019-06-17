package io.github.mklkj.filmowy.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.mklkj.filmowy.ui.MainActivity

@Module
@Suppress("unused")
internal abstract class BuilderModule {

    @ContributesAndroidInjector
    abstract fun binMainActivity(): MainActivity
}
