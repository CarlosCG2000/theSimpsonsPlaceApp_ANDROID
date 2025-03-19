package es.upsa.mimo.thesimpsonplace.data.sources.service.impl

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto
import es.upsa.mimo.thesimpsonplace.data.sources.service.CharacterDao

// Implementación de 'CharacterDtoDao' (acciones) en producción
class CharacterDaoJsonProd: CharacterDao {

    override fun getAllCharacters(): List<CharacterDto> {
        TODO("Llamará al json de producción")
    }

    override fun getFilterNameCharacters(name: String): List<CharacterDto> {
        TODO("Llamará al json de producción y filtraria por contenido de nombre")
    }
}