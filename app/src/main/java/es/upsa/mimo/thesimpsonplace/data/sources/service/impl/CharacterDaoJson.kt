package es.upsa.mimo.thesimpsonplace.data.sources.service.impl

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto
import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender
import es.upsa.mimo.thesimpsonplace.data.entities.character.ImageDto
import es.upsa.mimo.thesimpsonplace.data.mappers.toCharacter
import es.upsa.mimo.thesimpsonplace.data.sources.service.CharacterDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import kotlinx.serialization.json.Json
import java.io.IOException
import kotlin.text.get

// Implementación de 'CharacterDtoDao' (acciones) en testing (y preview) o producción
class CharacterDaoJson(val context: Context, val dataJson: String, val imagJson: String): CharacterDao {

    // json -> dependiendo del json va a ser para producción o para testing

    override suspend fun getAllCharacters(): List<CharacterDto> {
        // Configurar el deserializador de JSON
        val jsonFormat = Json { ignoreUnknownKeys = true }

        // Abrir el archivo JSON desde los assets
        val jsonPersonajes = context.assets.open(dataJson).bufferedReader().use { it.readText() }
        val charactersDto = jsonFormat.decodeFromString<List<CharacterDto>>(jsonPersonajes)

        // Leer y parsear JSON de imágenes
        val jsonImagenes = context.assets.open(imagJson).bufferedReader().use { it.readText() }
        val imagenesDto = jsonFormat.decodeFromString<List<ImageDto>>(jsonImagenes)

        // Crear un mapa {nombre en minúsculas -> imagen}
        val imagesMap = imagenesDto.associateBy(
            { it.name?.lowercase() }, // Clave: nombre en minúsculas
            { it.image ?: "not_specified" } // Valor: URL de la imagen o "not_specified"
        )

        // Combinar personajes con imágenes
        return charactersDto.map { dto ->
            val imagen = imagesMap[dto.nombre?.lowercase()] ?: "not_specified" // Buscar imagen

            CharacterDto(
                id = dto.id,
                nombre = dto.nombre,
                genero = dto.genero,
                imagen = imagen ?: "not_specified"
            )
        }

        // Decodificar el JSON a una lista de objetos CharacterDto
        // return jsonFormat.decodeFromString<List<CharacterDto>>(json)
    }

    override suspend fun getCharactersByName(name: String): List<CharacterDto> {
        TODO("Llamará al json y filtraria por contenido de nombre")
        return getAllCharacters().filter { it.nombre?.contains(name, ignoreCase = true) == true }
    }
}