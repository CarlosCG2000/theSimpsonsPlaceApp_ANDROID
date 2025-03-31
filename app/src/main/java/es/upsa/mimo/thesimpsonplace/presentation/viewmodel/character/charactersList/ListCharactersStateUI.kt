package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList

import es.upsa.mimo.thesimpsonplace.domain.models.Character

data class ListCharactersStateUI (val characters:List<Character> = emptyList(),
                                  val isLoading: Boolean = false)
