package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesFilters

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

data class ListEpisodesFilterStateUI (
    var episodes:List<Episode> = emptyList(),
    val isLoading: Boolean = false)
