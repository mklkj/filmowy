package io.github.mklkj.filmowy.api

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val parts = response.body()?.string().orEmpty().split("\n")

        if ("ok" != parts[0]) {
            throw Exception(parts[1])
        }

        val content = parts.drop(1).joinToString("\n")
        val timeInfo = content.substring(content.lastIndexOf(" "))
        val newBody = content.replace(timeInfo, "")

        val body = ResponseBody.create(response.body()?.contentType(), newBody)
        return response.newBuilder().body(body).build()
    }
}
