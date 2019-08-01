package io.github.mklkj.filmowy.binding

import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import io.github.mklkj.filmowy.BuildConfig
import io.github.mklkj.filmowy.FilmowyApp
import io.github.mklkj.filmowy.binding.adapter.AttributeHelperBindingAdapter
import io.github.mklkj.filmowy.binding.adapter.DateBindingAdapter
import io.github.mklkj.filmowy.binding.adapter.ImageBindingAdapter

@Module
object BindingModule {

    @Provides
    @DataBinding
    fun provideContext(app: FilmowyApp): Context = app

    @Provides
    @DataBinding
    fun provideImageBindingAdapter(picasso: Picasso) = ImageBindingAdapter(picasso)

    @Provides
    @DataBinding
    fun provideDateBindingAdapter() = DateBindingAdapter()

    @Provides
    @DataBinding
    fun provideBooleanBindingAdapter() = AttributeHelperBindingAdapter()

    @Provides
    @DataBinding
    fun providePicasso(context: Context): Picasso = Picasso.Builder(context)
        .loggingEnabled(BuildConfig.DEBUG)
        .build()
}
