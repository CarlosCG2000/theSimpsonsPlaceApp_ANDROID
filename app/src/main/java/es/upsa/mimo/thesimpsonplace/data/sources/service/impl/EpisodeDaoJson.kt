package es.upsa.mimo.thesimpsonplace.data.sources.service.impl

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDto
import es.upsa.mimo.thesimpsonplace.data.sources.service.EpisodeDao
import es.upsa.mimo.thesimpsonplace.data.utils.toDate
import kotlinx.serialization.json.Json
import java.util.Date

class EpisodeDaoJson(val json: String /** el json puede ser de produccíon o de testing */): EpisodeDao {

    override fun getAllEpisodes(): List<EpisodeDto> {
        TODO("Not yet implemented")
        return Json.Default.decodeFromString< List<EpisodeDto> >(json)
    }

    override fun getEpisodeById(id: String): EpisodeDto? {
        TODO("Not yet implemented")
        return getAllEpisodes().firstOrNull { it.id == id }
    }

    override fun getEpisodesByTitle(title: String): List<EpisodeDto> {
        TODO("Not yet implemented")
        return getAllEpisodes().filter { it.titulo?.contains(title, ignoreCase = true) == true }
    }

    override fun getEpisodesByDate(minDate: Date?, maxDate: Date?): List<EpisodeDto> {
        TODO("Not yet implemented")
        return getAllEpisodes().filter { episode ->
            val date = episode.lanzamiento?.toDate() ?: return@filter false

            (minDate == null || date >= minDate) && (maxDate == null || date <= maxDate)
        }
    }

    override fun getEpisodesBySeason(season: Int): List<EpisodeDto> {
        TODO("Not yet implemented")
        return getAllEpisodes().filter { it.temporada == season }
    }

    override fun getEpisodesByChapter(chapter: Int): List<EpisodeDto> {
        TODO("Not yet implemented")
        return getAllEpisodes().filter { it.episodio == chapter }
    }

}