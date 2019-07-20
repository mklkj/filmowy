package io.github.mklkj.filmowy.api.interceptor

import io.github.mklkj.filmowy.api.safeSubstring
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val parts = response.body?.string().orEmpty().split("\n")

        if ("ok" != parts[0]) {
            throw Exception(parts[1])
        }

        val content = parts.drop(1).joinToString("\n")
        val timeInfo = content.safeSubstring(content.lastIndexOf(" "))

        if (content.startsWith("null")) {
            throw Exception("404")
        }

        val newBody = content
            .replace("exc NullPointerException", "[]")
            .run {
                if (timeInfo.startsWith(" t:")) replace(timeInfo, "")
                else this
            }

        val body = newBody.toResponseBody(response.body?.contentType())
        return response.newBuilder().body(body).build()
    }
}
