package io.github.mklkj.filmowy.api

import com.google.gson.JsonArray

fun JsonArray.getNullable(index: Int) = get(index).let { if(it.isJsonNull) null else it }

fun String.asMethod(vararg params: Int) = "$this ${params.joinToString(",", "[", "]")}\n"
