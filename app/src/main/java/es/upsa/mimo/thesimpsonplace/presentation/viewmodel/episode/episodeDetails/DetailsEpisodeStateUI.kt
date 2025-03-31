package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodeDetails

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

data class DetailsEpisodeStateUI (val episode: Episode? = null,
                                  val isLoading: Boolean = false)
