package es.upsa.mimo.thesimpsonplace.data.sources.remote.impl

import android.content.Context
import android.util.Log
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDTO
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodesDTO
import es.upsa.mimo.thesimpsonplace.data.sources.remote.EpisodeDao
import es.upsa.mimo.thesimpsonplace.utils.toDate
import kotlinx.serialization.json.Json
import java.util.Date

class EpisodeDaoJson(val context: Context,
                     val dataJson: String /** el json puede ser de produccíon o de testing */): EpisodeDao {

    override suspend fun getAllEpisodes(): List<EpisodeDTO> {

        val jsonFormat = Json { ignoreUnknownKeys = true }

        try {
            val json = context.assets.open(dataJson).bufferedReader().use { it.readText() }
            val episodios: List<EpisodeDTO> = jsonFormat.decodeFromString<EpisodesDTO>(json).episodios ?: emptyList()
            Log.i("getAllEpisodes", "$episodios")
            return episodios
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList() // Si hay error, devuelve una lista vacía
        }

    }

    override suspend fun getEpisodeById(id: String): EpisodeDTO? {
        return getAllEpisodes().firstOrNull { it.id == id }
    }

    override suspend fun getEpisodesByTitle(title: String): List<EpisodeDTO> {
        return  getAllEpisodes().filter { it.titulo?.contains(title, ignoreCase = true) == true }
    }

    override suspend fun getEpisodesByDate(minDate: Date?, maxDate: Date?): List<EpisodeDTO> {
        return getAllEpisodes().filter { episode ->
            val date = episode.lanzamiento?.toDate() ?: return@filter false

            (minDate == null || date >= minDate) && (maxDate == null || date <= maxDate)
        }
    }

    override suspend fun getEpisodesBySeason(season: Int): List<EpisodeDTO> {
        return if (season == 0) getAllEpisodes()
        else getAllEpisodes().filter { it.temporada == season }
    }

    override suspend fun getEpisodesByChapter(chapter: Int): List<EpisodeDTO> {
        return if (chapter == 0) getAllEpisodes()
              else getAllEpisodes().filter { it.episodio == chapter }
    }
}