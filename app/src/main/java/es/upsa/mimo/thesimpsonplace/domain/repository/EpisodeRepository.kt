package es.upsa.mimo.thesimpsonplace.domain.repository

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDb
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDto
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface EpisodeRepository {
    // Casos de uso de la datos obtenido de la fuente principal
    suspend fun getAllEpisodes(): List<Episode>
    suspend fun getEpisodeById(id: String): Episode?
    suspend fun getEpisodesByTitle(title: String,
                                   episodes: List<Episode> = emptyList()): List<Episode>
    suspend fun getEpisodesByDate(minDate: Date?, maxDate: Date?,
                                  episodes: List<Episode> = emptyList()): List<Episode>
    suspend fun getEpisodesBySeason(season:Int,
                                    episodes: List<Episode> = emptyList()): List<Episode>
    suspend fun getEpisodesByChapter(chapter:Int,
                                     episodes: List<Episode> = emptyList()): List<Episode>
    suspend fun getEpisodesByView(isView: Boolean,
                                     episodes: List<Episode> = emptyList()): List<Episode>
    suspend fun getEpisodesOrder(isAscendent: Boolean,
                                 episodes: List<Episode> = emptyList()): List<Episode>

    // Casos de uso de la datos de la base de datos
    fun getAllEpisodesDb(): Flow<List<Episode>>
    suspend fun getEpisodeDbById(episodeId: String): Episode?
    fun getWatchedEpisodes(): Flow<List<Episode>>
    suspend fun isEpisodeDbWatched(episodeId: String): Boolean?
    suspend fun isEpisodeDbFavorite(episodeId: String): Boolean?
    suspend fun insertEpisodeDb(episode: Episode)
    suspend fun updateEpisodeDbStatus(episodeId: String, esVisto: Boolean, esFavorito: Boolean)
}

