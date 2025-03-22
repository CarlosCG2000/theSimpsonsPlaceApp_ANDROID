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

        // Abrir el archivo JSON desde los assets
        val json = context.assets.open(json).bufferedReader().use { it.readText() }

        // Configurar el deserializador de JSON
        val jsonFormat = Json { ignoreUnknownKeys = true }

        // Decodificar el JSON a una lista de objetos CharacterDto
        return jsonFormat.decodeFromString<List<CharacterDto>>(json)
    }

    override suspend fun getCharactersByName(name: String): List<CharacterDto> {
        TODO("Llamará al json y filtraria por contenido de nombre")
        return getAllCharacters().filter { it.nombre?.contains(name, ignoreCase = true) == true }
    }
}