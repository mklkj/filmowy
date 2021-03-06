package io.github.mklkj.filmowy.api.interceptor

import android.content.SharedPreferences
import androidx.core.content.edit
import io.github.mklkj.filmowy.api.exception.BadCredentialsException
import io.github.mklkj.filmowy.api.exception.NotFoundException
import io.github.mklkj.filmowy.api.exception.NotLoggedInException
import io.github.mklkj.filmowy.api.pojo.UserData
import io.github.mklkj.filmowy.api.safeSubstring
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class ResponseInterceptor @Inject constructor(private val preferences: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val body = response.body?.string().orEmpty()

        // check for json response
        if (body.startsWith("{") || body.startsWith("[")) {
            return response.newBuilder().body(body.toResponseBody(response.body?.contentType())).build() // TODO: simplify this
        }

        if (response.networkResponse?.request?.url?.toString()?.contains("login_redirect") == true) handleNotLoggedIn()

        val parts = body.split("\n")

        val newBody = if (parts.size == 1) {
            proceedSearchResponse(parts[0])
        } else {
            proceedCommonResponse(parts)
        }

        return response.newBuilder().body(newBody.toResponseBody(response.body?.contentType())).build()
    }

    private fun proceedCommonResponse(parts: List<String>): String {
        if (parts[1].contains("badCreadentials")) throw BadCredentialsException("Zła nazwa użytkownika lub hasło")
        if (parts[1].contains("login_redirect")) handleNotLoggedIn()

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

    private fun handleNotLoggedIn() {
        preferences.edit { remove(UserData.KEY) }
        throw NotLoggedInException("Musisz być zalogowany by zobaczyć tą stronę")
    }
}
