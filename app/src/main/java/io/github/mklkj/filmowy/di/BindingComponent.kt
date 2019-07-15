package io.github.mklkj.filmowy.di

import androidx.databinding.DataBindingComponent
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Component
import io.github.mklkj.filmowy.binding.BooleanBindingAdapter
import io.github.mklkj.filmowy.binding.DateBindingAdapter
import io.github.mklkj.filmowy.binding.ImageBindingAdapter

@DataBinding
@Component(dependencies = [AppComponent::class], modules = [BindingModule::class])
interface BindingComponent : DataBindingComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun picasso(picasso: Picasso): Builder

        fun bindingModule(bindingModule: BindingModule): Builder
        fun bindAppComponent(appComponent: AppComponent): Builder
        fun build(): BindingComponent
    }

    override fun getImageBindingAdapter(): ImageBindingAdapter

    override fun getDateBindingAdapter(): DateBindingAdapter

    override fun getBooleanBindingAdapter(): BooleanBindingAdapter
}
