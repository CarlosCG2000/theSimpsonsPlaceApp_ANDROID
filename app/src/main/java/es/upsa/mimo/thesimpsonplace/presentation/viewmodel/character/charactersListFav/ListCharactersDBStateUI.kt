package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav

import es.upsa.mimo.thesimpsonplace.domain.entities.Character

data class ListCharactersDbStateUI (val charactersSet:Set<Int> = emptySet(),
                                    val characters:List<Character> = emptyList<Character>())
