package es.upsa.mimo.thesimpsonplace.data.sources.remote.impl

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto
import es.upsa.mimo.thesimpsonplace.data.entities.character.ImageDto
import es.upsa.mimo.thesimpsonplace.data.sources.remote.CharacterDao
import kotlinx.serialization.json.Json
import javax.inject.Named

// Implementación de 'CharacterDtoDao' (acciones) en testing (y preview) o producción
// @Inject constructor(...) -> Permite que Hilt maneje la creación de la clase.
//@Singleton
class CharacterDaoJson /* ❌ @Inject constructor (Usando @Provides) */(@ApplicationContext private val context: Context, // Evita la necesidad de pasar manualmente el contexto.
                                           @Named("dataJson") private val dataJson: String,
                                           @Named("imageJson") private val imagJson: String
                                           ): CharacterDao {

    // json -> dependiendo del json va a ser para producción o para testing
    override suspend fun getAllCharacters(): List<CharacterDto> {
        // Configurar el deserializador de JSON
        val jsonFormat = Json { ignoreUnknownKeys = true }

        try {
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

            Log.i("getAllCharacters", "$charactersDto")

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
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList() // Si hay error, devuelve una lista vacía
        }
    }

    override suspend fun getCharactersByName(name: String): List<CharacterDto> {
        return getAllCharacters().filter { it.nombre?.contains(name, ignoreCase = true) == true }
    }
}

