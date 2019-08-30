package io.github.mklkj.filmowy.api.interceptor

import android.os.Build.*
import android.os.Build.VERSION.RELEASE
import android.os.Build.VERSION.SDK_INT
import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .header("User-Agent", getUserAgent())
                .build()
        )
    }

    private fun getUserAgent(): String {
        return "Filmweb/1.0, Android $RELEASE (API: $SDK_INT, os-ver: ${System.getProperty("os.version")}), Device: $MANUFACTURER, $DEVICE ($MODEL)"
    }
}
