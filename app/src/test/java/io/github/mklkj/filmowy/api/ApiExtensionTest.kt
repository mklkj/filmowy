package io.github.mklkj.filmowy.api

import org.junit.Assert.assertEquals
import org.junit.Test

class ApiExtensionTest {

    @Test
    fun getPersonFilmsImageUrl() {
        assertEquals("https://ssl-gfx.filmweb.pl/po/00/10/10/7713834.0.jpg", "/00/10/10/7713834.2.jpg".getPersonFilmsImageUrl(38))
        assertEquals("https://ssl-gfx.filmweb.pl/po/00/10/10/7713834.4.jpg", "/00/10/10/7713834.2.jpg".getPersonFilmsImageUrl(70))
        assertEquals("https://ssl-gfx.filmweb.pl/po/00/10/10/7713834.1.jpg", "/00/10/10/7713834.2.jpg".getPersonFilmsImageUrl(90))
        assertEquals("https://ssl-gfx.filmweb.pl/po/00/10/10/7713834.2.jpg", "/00/10/10/7713834.2.jpg".getPersonFilmsImageUrl(140))
        assertEquals("https://ssl-gfx.filmweb.pl/po/00/10/10/7713834.6.jpg", "/00/10/10/7713834.2.jpg".getPersonFilmsImageUrl(200))
        assertEquals("https://ssl-gfx.filmweb.pl/po/00/10/10/7713834.5.jpg", "/00/10/10/7713834.2.jpg".getPersonFilmsImageUrl(370))
        assertEquals("https://ssl-gfx.filmweb.pl/po/00/10/10/7713834.3.jpg", "/00/10/10/7713834.2.jpg".getPersonFilmsImageUrl(500))
    }
}
