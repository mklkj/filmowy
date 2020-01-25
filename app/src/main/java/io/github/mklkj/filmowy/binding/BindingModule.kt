package io.github.mklkj.filmowy.binding

import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.mklkj.filmowy.FilmowyApp
import io.github.mklkj.filmowy.binding.adapter.ImageBindingAdapter

@Module
object BindingModule {

    @Provides
    @DataBinding
    fun provideContext(app: FilmowyApp): Context = app

    @Provides
    @DataBinding
    fun provideImageBindingAdapter() = ImageBindingAdapter()
}
