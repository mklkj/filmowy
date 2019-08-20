package io.github.mklkj.filmowy.api

import com.google.gson.GsonBuilder
import io.github.mklkj.filmowy.api.interceptor.ResponseInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

abstract class BaseApiTest {

    val server = MockWebServer()

    @After
    fun tearDown() {
        server.shutdown()
    }

    fun getResource(filename: String) = javaClass.getResource(filename)?.readText()

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(server.url("/").toString())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ResponseInterceptor())
                    .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                    .build()
            )
            .build()
    }

    fun getRetrofitScrapper(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(server.url("/").toString())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JspoonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                    .build()
            )
            .build()
    }

}
