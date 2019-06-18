package io.github.mklkj.filmowy.api

import android.net.Uri
import com.google.gson.JsonArray

fun JsonArray.getNullable(index: Int) = get(index).let { if (it.isJsonNull) null else it }

fun String.asMethod(vararg params: Int) = "$this ${params.joinToString(",", "[", "]")}\n"

fun String.getImageUrl(width: Int = 90): Uri = ("https://ssl-gfx.filmweb.pl/ph" + when (width) {
    in 0..90 -> this
    in 91..180 -> replace("0.jpg", "2.jpg")
    in 181..450 -> replace("0.jpg", "3.jpg")
    else -> replace("0.jpg", "1.jpg")
}).run { Uri.parse(this) }
