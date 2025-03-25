package es.upsa.mimo.thesimpsonplace.domain.repository

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDto
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
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

    // Casos de uso de la datos de la base de datos
    suspend fun getAllEpisodesDb(): List<Episode>
    suspend fun getEpisodeByIdDb(id: String): Episode?
    suspend fun getEpisodeByIdsDb(ids: List<String>): List<Episode>?
    suspend fun updateEpisodeDb(id: String, esView: Boolean, isFav: Boolean): Unit
    suspend fun insertEpisodeDb(episode: Episode, esView: Boolean, isFav: Boolean): Unit
}