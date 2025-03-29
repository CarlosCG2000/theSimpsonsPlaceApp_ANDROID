package es.upsa.mimo.thesimpsonplace.data.mappers

// import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDb
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDb
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto
import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender
import es.upsa.mimo.thesimpsonplace.domain.entities.Character

// Función de extensión de 'CharacterDto' para mapear a 'Character'
fun CharacterDto.toCharacter()
    = Character (
        id = getIdAsInt() ?: 0, // Convierte id a Int o usa 0 por defecto
        nombre = nombre ?: "Unknown", // Si el nombre es null, usa "Unknown"
        genero = Gender.fromAbbreviation(genero), // Convierte "m" -> Male, "f" -> Female
//      esFavorito = false    // por defecto es 'false'
        imagen = imagen ?: "not_specified"
    )

// ROOM
fun Character.toCharacterDb(): CharacterDb {
    return CharacterDb( id = id,
                        nombre = nombre,
                        genero = genero,
                        imagen = imagen)
}
/**
    // Mapper: Convertir CharacterEntity a Character
    fun CharacterEntity.toCharacter(): Character {
        return Character(
            id = id,
            nombre = nombre,
            imagen = imagen,
            esFavorito = esFavorito
        )
    }

    // Mapper: Convertir Character a CharacterEntity
    fun Character.toCharacterEntity(): CharacterEntity {
        return CharacterEntity(
            id = id,
            nombre = nombre,
            imagen = imagen,
            esFavorito = esFavorito
        )
    }
 */