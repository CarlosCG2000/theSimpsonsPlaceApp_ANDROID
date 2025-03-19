package es.upsa.mimo.thesimpsonplace.data.sources.service.impl

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto
import es.upsa.mimo.thesimpsonplace.data.sources.service.CharacterDao
import kotlinx.serialization.json.Json

// Implementación de 'CharacterDtoDao' (acciones) en testing (y preview) o producción
class CharacterDaoJson (val json: String): CharacterDao {

    // json -> dependiendo del json va a ser para producción o para testing

    override fun getAllCharacters(): List<CharacterDto> {
        TODO("Llamará al json")
        return Json.decodeFromString< List<CharacterDto> >(json)
    }

    override fun getCharactersByName(name: String): List<CharacterDto> {
        TODO("Llamará al json y filtraria por contenido de nombre")
        return getAllCharacters().filter { it.nombre?.contains(name, ignoreCase = true) == true }
    }
}