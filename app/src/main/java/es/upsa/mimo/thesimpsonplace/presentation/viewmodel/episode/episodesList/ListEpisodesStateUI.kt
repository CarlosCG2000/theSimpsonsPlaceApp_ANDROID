package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

data class ListEpisodesStateUI (val episodes:List<Episode> = emptyList(),
                                val isLoading: Boolean = false)
