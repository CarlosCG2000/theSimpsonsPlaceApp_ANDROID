package es.upsa.mimo.thesimpsonplace.data.sources.service

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDto
import java.util.Date

// Interfaz con métodos para acceder a episodios desde el servicio.
interface EpisodeDao {
    fun getAllEpisodes(): List<EpisodeDto>
    fun getEpisodeById(id: String): EpisodeDto?
    fun getEpisodesByTitle(title: String): List<EpisodeDto>
    fun getEpisodesByDate(minDate: Date?, maxDate: Date?): List<EpisodeDto>
    fun getEpisodesBySeason(season:Int): List<EpisodeDto>
    fun getEpisodesByChapter(chapter:Int): List<EpisodeDto>
}