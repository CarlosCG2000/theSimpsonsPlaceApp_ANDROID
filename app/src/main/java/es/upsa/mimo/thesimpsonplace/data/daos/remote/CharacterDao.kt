package es.upsa.mimo.thesimpsonplace.data.daos.remote

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDTO

/**
     Toda fuente de datos de Personajes debe de implementar esta interfaz, con sus correspondientes acciones
     Las funciones deben de ser suspend para ejecutarse en I/O
     Se hace llamada a un json local en assets
 */
interface CharacterDao {
    suspend fun getAllCharacters(): List<CharacterDTO> // Debe ser suspend para ejecutarse en I/O
    suspend fun getCharactersByName(name: String): List<CharacterDTO>
}