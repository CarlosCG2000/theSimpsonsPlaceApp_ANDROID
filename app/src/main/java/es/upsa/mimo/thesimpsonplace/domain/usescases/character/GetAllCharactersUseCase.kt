package es.upsa.mimo.thesimpsonplace.domain.usescases.character

import es.upsa.mimo.thesimpsonplace.domain.entities.Character

interface GetAllCharactersUseCase {
    /*suspend*/ fun execute(): List<Character>
}