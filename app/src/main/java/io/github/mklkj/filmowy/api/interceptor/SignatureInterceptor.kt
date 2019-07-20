package io.github.mklkj.filmowy.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest

class SignatureInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("version", "1.0")
            .addQueryParameter("appId", "android")
            .addQueryParameter("signature", getMethodSignature(request.url.queryParameter("methods")))
            .build()
        return chain.proceed(request.newBuilder().url(url.toString()).build())
    }

    private fun getMethodSignature(method: String?): String {
        return "1.0," + (method + "android" + "qjcGhW2JnvGT9dfCt3uT_jozR3s").md5()
    }

    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }
}
