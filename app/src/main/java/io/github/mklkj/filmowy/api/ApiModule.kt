package io.github.mklkj.filmowy.api

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.github.mklkj.filmowy.BuildConfig
import io.github.mklkj.filmowy.api.interceptor.ResponseInterceptor
import io.github.mklkj.filmowy.api.interceptor.SignatureInterceptor
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideApiService(@Named("json") retrofit: Retrofit): ApiService = retrofit.create()

    @Singleton
    @Provides
    fun provideScrapperService(@Named("scrapper") retrofit: Retrofit): ScrapperService = retrofit.create()

    @Singleton
    @Provides
    @Named("json")
    fun provideRetrofitJson(client: OkHttpClient.Builder): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.filmweb.pl/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(
            client
                .addInterceptor(SignatureInterceptor())
                .addInterceptor(ResponseInterceptor()).build()
        )
        .build()

    @Singleton
    @Provides
    @Named("scrapper")
    fun provideRetrofitScrapper(client: OkHttpClient.Builder): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.filmweb.pl/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(JspoonConverterFactory.create())
        .client(client.build())
        .build()

    @Singleton
    @Provides
    fun provideOkHttpBuilder(cookieJar: CookieJar): OkHttpClient.Builder = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .followRedirects(true)
        .callTimeout(30, TimeUnit.SECONDS)
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        })

    @Singleton
    @Provides
    fun provideCookieJar(context: Context): CookieJar {
        return PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
    }
}
