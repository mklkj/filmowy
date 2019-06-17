package io.github.mklkj.filmowy.di

import dagger.Component
import io.github.mklkj.filmowy.api.BaseApiTest
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
    ]
)
interface TestAppComponent : AppComponent {

    fun inject(baseTest: BaseApiTest)
}
