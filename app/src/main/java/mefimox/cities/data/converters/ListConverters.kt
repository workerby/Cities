package mefimox.cities.data.converters

import android.util.Log
import com.google.gson.Gson
import kotlin.collections.toList

fun List<Long>.toJson() = Gson().toJson(this)

fun String.fromJsonToList(): List<Long> =
    try {
        Gson().fromJson(this, Array<Long>::class.java).toList()
    } catch (e: Exception) {
        Log.e("ListConverters: String.toList()", e.toString())
        emptyList()
    }
