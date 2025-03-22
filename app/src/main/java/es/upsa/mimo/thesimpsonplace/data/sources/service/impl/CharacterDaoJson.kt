package es.upsa.mimo.thesimpsonplace.data.sources.service.impl

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto
import es.upsa.mimo.thesimpsonplace.data.sources.service.CharacterDao
import kotlinx.serialization.json.Json
import java.io.IOException

// Implementación de 'CharacterDtoDao' (acciones) en testing (y preview) o producción
class CharacterDaoJson(val context: Context, val json: String): CharacterDao {

    // json -> dependiendo del json va a ser para producción o para testing

    override suspend fun getAllCharacters(): List<CharacterDto> {
        val jsonString = try {
            context.assets.open(json ).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            return emptyList()
        }

        val gson = Gson()
        val listType = object : TypeToken<List<Character>>() {}.type
        return gson.fromJson(jsonString, listType)
    }

    override suspend fun getCharactersByName(name: String): List<CharacterDto> {
        TODO("Llamará al json y filtraria por contenido de nombre")
        return getAllCharacters().filter { it.nombre?.contains(name, ignoreCase = true) == true }
    }
}