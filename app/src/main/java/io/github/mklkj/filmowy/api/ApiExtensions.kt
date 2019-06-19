package io.github.mklkj.filmowy.api

import android.net.Uri
import com.google.gson.JsonArray
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset

fun JsonArray.getNullable(index: Int) = elementAtOrNull(index).let { if (it?.isJsonNull == true) null else it }

fun String.safeSubstring(index: Int) = if (index == -1) this else substring(index)

fun String.asMethod(vararg params: Long) = "$this ${params.joinToString(",", "[", "]")}\n"

fun String.asMethod(vararg params: Int) = "$this ${params.joinToString(",", "[", "]")}\n"

fun String.getFilmImageUrl(width: Int = 90) = ("https://ssl-gfx.filmweb.pl/ph" + when (width) {
    in 0..90 -> this
    in 91..180 -> replace("0.jpg", "2.jpg")
    in 181..450 -> replace("0.jpg", "3.jpg")
    else -> replace("0.jpg", "1.jpg")
}).toUri()

fun String.getNewsImageUrl(width: Int = 90) = ("https://ssl-gfx.filmweb.pl/an" + when (width) {
    in 0..90 -> this
    in 91..230 -> replace("1.jpg", "5.jpg")
    in 231..250 -> replace("1.jpg", "3.jpg")
    in 251..265 -> replace("1.jpg", "4.jpg")
    in 266..320 -> replace("1.jpg", "9.jpg")
    in 321..480 -> replace("1.jpg", "10.jpg")
    in 481..500 -> replace("1.jpg", "6.jpg")
    in 501..640 -> replace("1.jpg", "2.jpg")
    in 641..1024 -> replace("1.jpg", "8.jpg")
    in 1025..1200 -> replace("1.jpg", "7.jpg")
    in 1201..1360 -> replace("1.jpg", "13.jpg")

    else -> replace("0.jpg", "1.jpg")
}).toUri()

fun String.getUserImageUrl(width: Int = 90) = ("https://ssl-gfx.filmweb.pl/u" + when (width) {
    in 0..75 -> replace("0.jpg", "3.jpg")
    in 76..80 -> replace("0.jpg", "2.jpg")
    else -> replace("0.jpg", "1.jpg")
}).toUri()

fun String.toUri(): Uri = Uri.parse(this)

fun Long.toLocalDateTime(): LocalDateTime = Instant
    .ofEpochMilli(this)
    .atZone(ZoneId.systemDefault())
    .withZoneSameInstant(ZoneOffset.UTC)
    .toLocalDateTime()
