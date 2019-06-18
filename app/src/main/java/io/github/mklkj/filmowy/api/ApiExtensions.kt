package io.github.mklkj.filmowy.api

import com.google.gson.JsonArray

fun JsonArray.getNullable(index: Int) = if (get(index).isJsonNull) null else this

fun String.asMethod(vararg params: Int) = "$this ${params.joinToString(",", "[", "]")}\n"
