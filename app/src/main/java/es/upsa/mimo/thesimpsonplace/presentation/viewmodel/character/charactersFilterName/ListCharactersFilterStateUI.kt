package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersFilterName

import es.upsa.mimo.thesimpsonplace.domain.models.Character

data class ListCharactersFilterStateUI (val characters:List<Character> = emptyList(),
                                        val isLoading: Boolean = false)

