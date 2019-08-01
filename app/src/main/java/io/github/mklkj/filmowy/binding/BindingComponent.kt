package io.github.mklkj.filmowy.binding

import androidx.databinding.DataBindingComponent
import dagger.BindsInstance
import dagger.Component
import io.github.mklkj.filmowy.FilmowyApp
import io.github.mklkj.filmowy.binding.adapter.AttributeHelperBindingAdapter
import io.github.mklkj.filmowy.binding.adapter.DateBindingAdapter
import io.github.mklkj.filmowy.binding.adapter.ImageBindingAdapter
import io.github.mklkj.filmowy.di.AppComponent

@DataBinding
@Component(dependencies = [AppComponent::class], modules = [BindingModule::class])
interface BindingComponent : DataBindingComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: FilmowyApp): Builder

        fun bindingModule(bindingModule: BindingModule): Builder
        fun bindAppComponent(appComponent: AppComponent): Builder
        fun build(): BindingComponent
    }

    override fun getImageBindingAdapter(): ImageBindingAdapter

    override fun getDateBindingAdapter(): DateBindingAdapter

    override fun getAttributeHelperBindingAdapter(): AttributeHelperBindingAdapter
}
