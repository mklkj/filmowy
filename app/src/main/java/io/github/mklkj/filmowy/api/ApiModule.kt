package io.github.mklkj.filmowy.api

import android.content.Context
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.mklkj.filmowy.BuildConfig
import io.github.mklkj.filmowy.api.interceptor.ResponseInterceptor
import io.github.mklkj.filmowy.api.interceptor.SignatureInterceptor
import io.github.mklkj.filmowy.api.interceptor.UserAgentInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideApiService(
        okHttpClient: OkHttpClient,
        cookieJar: ClearableCookieJar,
        responseInterceptor: ResponseInterceptor
    ): ApiService = Retrofit.Builder()
        .baseUrl("https://www.filmweb.pl/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(
            okHttpClient.newBuilder()
                .cookieJar(cookieJar)
                .followRedirects(true)
                .callTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BASIC
                })
                .addInterceptor(SignatureInterceptor())
                .addInterceptor(responseInterceptor)
                .build()
        )
        .build()
        .create()

    @Singleton
    @Provides
    fun provideScrapperService(okHttpClient: OkHttpClient, cookieJar: ClearableCookieJar): ScrapperService = Retrofit.Builder()
        .baseUrl("https://www.filmweb.pl/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(JspoonConverterFactory.create())
        .client(
            okHttpClient.newBuilder()
                .cookieJar(cookieJar)
                .followRedirects(true)
                .callTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(UserAgentInterceptor())
                .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BASIC
                }).build()
        )
        .build()
        .create()

    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient()

    @Singleton
    @Provides
    fun provideCookieJar(@ApplicationContext context: Context): ClearableCookieJar {
        return PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context), true)
    }
}
