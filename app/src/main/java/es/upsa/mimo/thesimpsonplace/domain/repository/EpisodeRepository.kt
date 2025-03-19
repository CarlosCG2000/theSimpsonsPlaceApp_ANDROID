package es.upsa.mimo.thesimpsonplace.domain.repository

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import java.util.Date

interface EpisodeRepository {
    // Casos de uso de la datos obtenido de la fuente principal
    fun getAllEpisodes(): List<Episode>
    fun getEpisodeById(id: String): Episode?
    fun getEpisodesByTitle(title: String): List<Episode>
    fun getEpisodesByDate(minDate: Date?, maxDate: Date?): List<Episode>
    fun getEpisodesBySeason(season:Int): List<Episode>
    fun getEpisodesByChapter(chapter:Int): List<Episode>

    // Casos de uso de la datos de la base de datos
    fun getAllEpisodesDb(): List<Episode>
    fun getEpisodeByIdDb(id: String): Episode?
    fun getEpisodeByIdsDb(ids: List<String>): List<Episode>?
    fun updateEpisodeDb(id: String, esView: Boolean, isFav: Boolean): Unit
    fun insertEpisodeDb(episode: Episode, esView: Boolean, isFav: Boolean): Unit
}