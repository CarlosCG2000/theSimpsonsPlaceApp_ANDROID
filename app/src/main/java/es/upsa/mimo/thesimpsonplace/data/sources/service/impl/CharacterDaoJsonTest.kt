package es.upsa.mimo.thesimpsonplace.data.sources.service.impl

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto
import es.upsa.mimo.thesimpsonplace.data.sources.service.CharacterDao

// Implementación de 'CharacterDtoDao' (acciones) en Testing o Preview
class CharacterDaoJsonTest: CharacterDao {

    override fun getAllCharacters(): List<CharacterDto> {
        TODO("Llamará al json de test")
    }

    override fun getFilterNameCharacters(name: String): List<CharacterDto> {
        TODO("Llamará al json de test y filtraria por contenido de nombre")
    }

}