package es.upsa.mimo.thesimpsonplace.data.daos.remote.impl

import android.content.Context
import android.util.Log
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDTO
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodesDTO
import es.upsa.mimo.thesimpsonplace.data.daos.remote.EpisodeDao
import es.upsa.mimo.thesimpsonplace.utils.toDate
import kotlinx.serialization.json.Json
import java.util.Date

class EpisodeDaoImpl( val context: Context,
                      /** el json puede ser de producción o de testing */
                      val dataJson: String ): EpisodeDao {

    override suspend fun getAllEpisodes(): List<EpisodeDTO> {
        val jsonFormat = Json { ignoreUnknownKeys = true }

        try {
            val json = context.assets.open(dataJson)
                                        .bufferedReader()
                                        .use { it.readText() }
            val episodios: List<EpisodeDTO> = jsonFormat.decodeFromString<EpisodesDTO>(json).episodios ?: emptyList()
            return episodios
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    // Funciones de filtrado de episodios
    override suspend fun getEpisodeById(id: String): EpisodeDTO? =
         getAllEpisodes().firstOrNull { it.id == id }

    override suspend fun getEpisodesByTitle(title: String): List<EpisodeDTO> =
         getAllEpisodes().filter { it.titulo?.contains(title, ignoreCase = true) == true }

    override suspend fun getEpisodesByDate(minDate: Date?, maxDate: Date?): List<EpisodeDTO> =
         getAllEpisodes().filter { episode ->
            val date = episode.lanzamiento?.toDate() ?: return@filter false
            (minDate == null || date >= minDate) && (maxDate == null || date <= maxDate)
        }

    override suspend fun getEpisodesBySeason(season: Int): List<EpisodeDTO> =
        if (season == 0) getAllEpisodes()
        else getAllEpisodes().filter { it.temporada == season }

    override suspend fun getEpisodesByChapter(chapter: Int): List<EpisodeDTO> =
        if (chapter == 0) getAllEpisodes()
        else getAllEpisodes().filter { it.episodio == chapter }
}