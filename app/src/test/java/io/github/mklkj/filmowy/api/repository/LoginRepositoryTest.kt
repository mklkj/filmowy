package io.github.mklkj.filmowy.api.repository

import android.content.SharedPreferences
import io.github.mklkj.filmowy.api.BaseApiTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.create

class LoginRepositoryTest : BaseApiTest() {

    @Mock
    private lateinit var preferences: SharedPreferences

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    private val loginRepository by lazy { LoginRepository(getRetrofit().create(), getRetrofitScrapper().create(), preferences) }

    @Test
    fun login() {
        server.enqueue(MockResponse().setBody(getResource("settings.html")!!))
        server.start()

        val user = loginRepository.login("", "").blockingGet()
        user.run {
            assertEquals(1234567, userId)
            assertEquals("jankowalsky", nick)
            assertEquals("Jan Kowalsky", name)
            assertEquals(1, gender)
            assertEquals("/55/60/2515560/2515560.2.jpg", imagePath)
        }
    }
}
