package io.github.mklkj.filmowy.api.interceptor

import io.github.mklkj.filmowy.api.exception.BadCredentialsException
import io.github.mklkj.filmowy.api.exception.NotFoundException
import io.github.mklkj.filmowy.api.exception.NotLoggedInException
import io.github.mklkj.filmowy.api.safeSubstring
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val parts = response.body?.string().orEmpty().split("\n")

        val newBody = if (parts.size == 1) {
            proceedSearchResponse(parts[0])
        } else {
            proceedCommonResponse(parts)
        }

        return response.newBuilder().body(newBody.toResponseBody(response.body?.contentType())).build()
    }

    private fun proceedCommonResponse(parts: List<String>): String {
        if (parts[1].contains("badCreadentials")) throw BadCredentialsException("Zła nazwa użytkownika lub hasło")
        if (parts[1].contains("login_redirect")) throw NotLoggedInException("Musisz być zalogowany by zobaczyć tą stronę")

        if ("ok" != parts[0]) throw Exception(parts[1])

        val content = parts.drop(1).joinToString("\n")
        val timeInfo = content.safeSubstring(content.lastIndexOf(" "))

        if (content.startsWith("null")) throw NotFoundException("404")

        return content
            .replace("exc NullPointerException", "[]")
            .run {
                if (timeInfo.startsWith(" t:")) replace(timeInfo, "")
                else this
            }
    }

    private fun proceedSearchResponse(response: String): String {
        return response.split("\\a").joinToString(",", "[", "]") {
            it.split("\\c").joinToString(",", "[", "]") { value -> "\"$value\"" }
        }
    }
}
