package io.github.mklkj.filmowy.api

import android.annotation.SuppressLint
import android.net.Uri
import com.google.gson.JsonArray
import org.threeten.bp.*
import org.threeten.bp.Instant.ofEpochMilli
import java.text.SimpleDateFormat

fun JsonArray.getNullable(index: Int) = elementAtOrNull(index).let { if (it?.isJsonNull == true) null else it }

@SuppressLint("SimpleDateFormat")
fun String.toLocalDate(format: String): LocalDate = ofEpochMilli(SimpleDateFormat(format).parse(this).time).atZone(ZoneId.systemDefault()).toLocalDate()

fun String.safeSubstring(index: Int) = if (index == -1) this else substring(index)

fun <T> Array<out T>.joinNotEmptyToString(separator: String, prefix: String, postfix: String): String =
    if (isNotEmpty()) joinToString(separator, prefix, postfix) else ""

fun String.asMethod(vararg params: Any) = "$this${params.joinNotEmptyToString(",", " [", "]")}\n"

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

fun String.getPersonImageUrl(width: Int = 90) = ("https://ssl-gfx.filmweb.pl/p" + when (width) {
    in 0..70 -> replace("1.jpg", "0.jpg")
    in 71..140 -> this
    in 141..200 -> replace("1.jpg", "2.jpg")
    in 201..360 -> replace("1.jpg", "3.jpg")
    in 361..500 -> replace("1.jpg", "4.jpg")
    else -> replace("1.jpg", "5.jpg")
}).toUri()

fun String.toUri(): Uri = Uri.parse(this)

fun Long.toLocalDateTime(): LocalDateTime = Instant
    .ofEpochMilli(this)
    .atZone(ZoneId.systemDefault())
    .withZoneSameInstant(ZoneOffset.UTC)
    .toLocalDateTime()
