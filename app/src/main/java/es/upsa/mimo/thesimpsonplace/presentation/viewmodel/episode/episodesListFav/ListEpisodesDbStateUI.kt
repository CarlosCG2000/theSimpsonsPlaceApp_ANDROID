package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

data class ListEpisodesDbStateUI(val episodesSet: Set<String> = emptySet(),
                                 val episodesViewSet: Set<String> = emptySet(),
                                 val episodesFavSet: Set<String> = emptySet(),
                                 val episodes:List<Episode> = emptyList<Episode>(),
                                 val episodesView: List<Episode> = emptyList<Episode>(),
                                 val episodesFav: List<Episode> = emptyList<Episode>()
)


