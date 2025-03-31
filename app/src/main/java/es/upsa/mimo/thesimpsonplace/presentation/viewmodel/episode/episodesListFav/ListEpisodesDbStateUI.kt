package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

data class ListEpisodesDbStateUI(val episodesSet: Set<String> = emptySet(),
                                 val episodesViewSet: Set<String> = emptySet(),
                                 val episodesFavSet: Set<String> = emptySet(),
                                 val episodes:List<Episode> = emptyList<Episode>(),
                                 val episodesView: List<Episode> = emptyList<Episode>(),
                                 val episodesFav: List<Episode> = emptyList<Episode>(),
                                 val isLoading: Boolean = false
)


