package es.upsa.mimo.thesimpsonplace.data.daos.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL
import java.util.Date

class TheSimpsonsConverters {
    private val gson = Gson()

    // 🔥 Convertir `Date` a `Long` y viceversa
    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(timestamp: Long): Date = timestamp.let { Date(it) }

    // 🔥 Convertir `List<String>` a `String` (JSON) y viceversa
    @TypeConverter
    fun fromStringList(list: List<String>): String = gson.toJson(list)

    @TypeConverter
    fun toStringList(data: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType) ?: emptyList()
    }

    // 🔥 Convertir `URL` a `String` y viceversa
    @TypeConverter
    fun fromUrl(url: URL): String = url.toString()

    @TypeConverter
    fun toUrl(urlString: String): URL = URL(urlString)
}
