package es.upsa.mimo.thesimpsonplace.data.daos.remote.impl

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDTO
import es.upsa.mimo.thesimpsonplace.data.entities.character.ImageDTO
import es.upsa.mimo.thesimpsonplace.data.daos.remote.CharacterDao
import kotlinx.serialization.json.Json
import javax.inject.Named

// Implementación de 'CharacterDtoDao' (acciones) en testing (y preview) o producción
class CharacterDaoImpl /** @Inject constructor (uso final con @Provides) */ (@ApplicationContext private val context: Context, // evita la necesidad de pasar manualmente el contexto
                                                                      /** jsons -> dependiendo del json va a ser para producción o para testing */
                                                                      @Named("dataJson") private val dataJson: String,
                                                                      @Named("imageJson") private val imagJson: String
                                           ): CharacterDao {

    override suspend fun getAllCharacters(): List<CharacterDTO> {

        val jsonFormat = Json { ignoreUnknownKeys = true } // Configurar el deserializador de JSON

        try {
            // Abrir loa archivo JSONs desde los assets y leerlos
            val jsonPersonajes = context.assets.open(dataJson)
                                                .bufferedReader()
                                                .use { it.readText() }

            val jsonImagenes = context.assets.open(imagJson)
                                                .bufferedReader()
                                                .use { it.readText() }
            // Parsear JSONs
            val charactersDto = jsonFormat.decodeFromString<List<CharacterDTO>>(jsonPersonajes)
            val imagenesDto = jsonFormat.decodeFromString<List<ImageDTO>>(jsonImagenes)

            // Crear un mapa {nombre en minúsculas -> imagen}
            val imagesMap = imagenesDto.associateBy (
                { it.name?.lowercase() },       // Clave: nombre en minúsculas
                { it.image ?: "not_specified" } // Valor: URL de la imagen o "not_specified"
            )

            Log.i("getAllCharacters", "$charactersDto")

            // Combinar personajes con imágenes (combinación de Jsons)
            return charactersDto.map { dto ->
                val imagen = imagesMap[dto.nombre?.lowercase()] ?: "not_specified"
                CharacterDTO( id = dto.id, nombre = dto.nombre, genero = dto.genero,  imagen = imagen )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList() // Si hay error, devuelve una lista vacía
        }
    }

    override suspend fun getCharactersByName(name: String): List<CharacterDTO> =
         getAllCharacters().filter { it.nombre?.
                                        contains(name, ignoreCase = true) == true }
}

