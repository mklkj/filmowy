package io.github.mklkj.filmowy.di

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import io.github.mklkj.filmowy.binding.DateBindingAdapter
import io.github.mklkj.filmowy.binding.ImageBindingAdapter

@Module
object BindingModule {

    @Provides
    @DataBinding
    fun provideImageBindingAdapter(picasso: Picasso) = ImageBindingAdapter(picasso)

    @Provides
    @DataBinding
    fun provideDateBindingAdapter() = DateBindingAdapter()
}
