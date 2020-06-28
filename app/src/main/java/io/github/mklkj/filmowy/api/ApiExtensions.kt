package io.github.mklkj.filmowy.api

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.pojo.Film
import io.reactivex.Flowable
import java.text.SimpleDateFormat
import java.time.Instant.ofEpochMilli
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

fun JsonArray.getNullable(index: Int) = elementAtOrNull(index).let { if (it?.isJsonNull == true) null else it }

@SuppressLint("SimpleDateFormat")
private fun String.toZonedTime(format: String) =
    ofEpochMilli(SimpleDateFormat(format).parse(this)!!.time).atZone(ZoneId.systemDefault())

fun String.toLocalDate(format: String = "yyyy-MM-dd"): LocalDate = toZonedTime(format).toLocalDate()

fun String.toLocalDateTime(format: String = "yyyy-MM-dd HH:mm:ss"): LocalDateTime =
    toZonedTime(format).toLocalDateTime()

fun String.safeSubstring(index: Int) = if (index == -1) this else substring(index)

fun <T> Array<out T>.joinNotEmptyToString(separator: String, prefix: String, postfix: String): String =
    if (isNotEmpty()) joinToString(separator, prefix, postfix) else ""

fun String.asMethod(vararg params: Any) = "$this${params.joinNotEmptyToString(",", " [", "]")}\n"

fun String.asVarargMethod(vararg params: Any) = "$this${params.joinNotEmptyToString(",", " [[", "]]")}\n"

fun String.getFilmImageUrl(width: Int = 90) = ("https://fwcdn.pl/ph" + when (width) {
    in 0..90 -> this
    in 91..180 -> replace("0.jpg", "2.jpg")
    in 181..450 -> replace("0.jpg", "3.jpg")
    else -> replace("0.jpg", "1.jpg")
})

fun String.getNewsImageUrl(width: Int = 90) = when (width) {
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
}

fun String.getUserImageUrl(width: Int = 90) = ("https://fwcdn.pl/u" + when (width) {
    in 0..75 -> replace("0.jpg", "3.jpg")
    in 76..80 -> replace("0.jpg", "2.jpg")
    else -> replace("0.jpg", "1.jpg")
})

fun String.getPersonImageUrl(width: Int = 90) = ("https://fwcdn.pl/p" + when (width) {
    in 0..70 -> replace("1.jpg", "0.jpg")
    in 71..140 -> this
    in 141..200 -> replace("1.jpg", "2.jpg")
    in 201..360 -> replace("1.jpg", "3.jpg")
    in 361..500 -> replace("1.jpg", "4.jpg")
    else -> replace("1.jpg", "5.jpg")
})

fun String.getPersonFilmsImageUrl(width: Int = 90) = ("https://fwcdn.pl/po" + when (width) {
    in 0..38 -> replace("2.jpg", "0.jpg")
    in 39..70 -> replace("2.jpg", "4.jpg")
    in 71..90 -> replace("2.jpg", "1.jpg")
    in 91..140 -> this
    in 141..200 -> replace("2.jpg", "6.jpg")
    in 201..370 -> replace("2.jpg", "5.jpg")
    else -> replace("2.jpg", "3.jpg")
})

fun Film.encodeName() = "${title.encodeFilmName()}-$year-$filmId"

fun Film.toUrl() = "https://m.filmweb.pl/film/${encodeName()}"

fun String.encodeFilmName() = replace("+", "%2B")
    .replace(" ", "+")
    .replace("?", "")
    .replace("%", "")

fun Long.toLocalDateTime(): LocalDateTime = ofEpochMilli(this)
    .atZone(ZoneId.systemDefault())
    .withZoneSameInstant(ZoneId.of("Europe/Warsaw"))
    .toLocalDateTime()

fun Long.toLocalDate(): LocalDate = ofEpochMilli(this)
    .atZone(ZoneId.systemDefault())
    .withZoneSameInstant(ZoneId.of("Europe/Warsaw"))
    .toLocalDate()

fun <T> LiveData<T>.toFlowable(owner: LifecycleOwner): Flowable<T> =
    Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(owner, this))

fun <T> Flowable<T>.toLiveData(): LiveData<T> = LiveDataReactiveStreams.fromPublisher(this)
