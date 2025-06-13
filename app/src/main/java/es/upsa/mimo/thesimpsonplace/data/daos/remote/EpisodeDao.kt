package es.upsa.mimo.thesimpsonplace.data.daos.remote

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDTO
import java.util.Date

/**
    Interfaz con métodos para acceder a episodios desde el servicio.
    Se hace llamada a un json local en assets
*/
interface EpisodeDao {
    suspend fun getAllEpisodes(): List<EpisodeDTO>
    suspend fun getEpisodeById(id: String): EpisodeDTO?
    suspend fun getEpisodesByTitle(title: String): List<EpisodeDTO>
    suspend fun getEpisodesByDate(minDate: Date?, maxDate: Date?): List<EpisodeDTO>
    suspend fun getEpisodesBySeason(season:Int): List<EpisodeDTO>
    suspend fun getEpisodesByChapter(chapter:Int): List<EpisodeDTO>
}