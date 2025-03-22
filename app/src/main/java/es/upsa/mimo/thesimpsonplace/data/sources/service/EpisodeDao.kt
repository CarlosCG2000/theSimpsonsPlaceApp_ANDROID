package es.upsa.mimo.thesimpsonplace.data.sources.service

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDto
import java.util.Date

// Interfaz con métodos para acceder a episodios desde el servicio.
interface EpisodeDao {
    suspend fun getAllEpisodes(): List<EpisodeDto> // Debe ser suspend para ejecutarse en I/O
    suspend fun getEpisodeById(id: String): EpisodeDto?
    suspend fun getEpisodesByTitle(title: String): List<EpisodeDto>
    suspend fun getEpisodesByDate(minDate: Date?, maxDate: Date?): List<EpisodeDto>
    suspend fun getEpisodesBySeason(season:Int): List<EpisodeDto>
    suspend fun getEpisodesByChapter(chapter:Int): List<EpisodeDto>
}