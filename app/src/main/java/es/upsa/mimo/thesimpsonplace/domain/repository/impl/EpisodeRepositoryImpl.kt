package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDto
import es.upsa.mimo.thesimpsonplace.data.mappers.toEpisode
import es.upsa.mimo.thesimpsonplace.data.sources.database.EpisodeDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.EpisodeDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class EpisodeRepositoryImpl(val dao: EpisodeDao, val databaseDao: EpisodeDatabaseDao): EpisodeRepository {

    suspend fun fusionSourceDB(episodesSource: List<Episode>): List<Episode> {
        return withContext(Dispatchers.IO) {
            val ids: List<String> = episodesSource.map { it.id } // Se optienen los ids de todos los episodios

            // 🚀 2️⃣ Cargar datos de la BD
            val episodesFromDb: Map<String, Episode> = databaseDao.getEpisodeByIdsDb(ids) // si hay episodios en la base de datos con dichos ids se recogen
                ?.associateBy { it.id } ?: emptyMap()

            // 🚀 3️⃣️ Resultado final de los datos
             episodesSource.map { episode ->
                val episodeDb = episodesFromDb[episode.id]

                if (episodeDb != null) {
                    episode.copy(
                        esFavorito = episodeDb.esFavorito,
                        esVisto = episodeDb.esVisto
                    )
                } else {
                    episode.copy()
                }
            }
        }
    }

    // _____________________________ DEL MOCK JSON O API _____________________________
    override suspend fun getAllEpisodes(): List<Episode> {
        return withContext(Dispatchers.IO) {
            // 🚀 1️⃣ Cargar datos del JSON/API
            val allEpisodesDto: List<EpisodeDto> =
                dao.getAllEpisodes() // Se obtienen todos los episodios de la funete de datos (mock Json, Api, etc)
            val allEpisodes: List<Episode> =
                allEpisodesDto.map { it.toEpisode() } // Se mapean a episodios para utilizar en las vistas

            fusionSourceDB(allEpisodes)
        }
    }

    override suspend fun getEpisodeById(id: String): Episode? {
        return withContext(Dispatchers.IO) {
            // 🚀 1️⃣ Cargar datos del JSON/API
            val episodeDto: EpisodeDto? = dao.getEpisodeById(id = id)
            val episode: Episode? = episodeDto?.toEpisode()

            // 🚀 2️⃣ Cargar datos de la BD
            val episodeDB: Episode? = databaseDao.getEpisodeByIdDb(id)

            // 🚀 3️⃣️ Resultado final de los datos
            if (episode != null && episodeDB != null){
                  episode.copy(
                    esFavorito = episodeDB.esFavorito,
                    esVisto = episodeDB.esVisto
                )
            } else{
                episode
            }

        }
    }

    override suspend fun getEpisodesByTitle(title: String): List<Episode> {
        return withContext(Dispatchers.IO) {
            // 🚀 1️⃣ Cargar datos del JSON/API
            val episodesDtoByTitle: List<EpisodeDto> = dao.getEpisodesByTitle(title)
            val episodesByTitle: List<Episode> = episodesDtoByTitle.map { it.toEpisode() }

            fusionSourceDB(episodesByTitle)
        }
    }

    override suspend fun getEpisodesByDate(minDate: Date?, maxDate: Date?): List<Episode> {
        return withContext(Dispatchers.IO) {
            val episodesDtoByDate: List<EpisodeDto> = dao.getEpisodesByDate(minDate, maxDate)
            val episodesByDate: List<Episode> = episodesDtoByDate.map { it.toEpisode() }

            fusionSourceDB(episodesByDate)
        }
    }

    override suspend fun getEpisodesBySeason(season: Int): List<Episode> {
        return withContext(Dispatchers.IO) {
            val episodesDtoBySeason: List<EpisodeDto> = dao.getEpisodesBySeason(season)
            val episodesBySeason: List<Episode> = episodesDtoBySeason.map { it.toEpisode() }

            fusionSourceDB(episodesBySeason)
        }
    }

    override suspend fun getEpisodesByChapter(chapter: Int): List<Episode> {
        return withContext(Dispatchers.IO) {
            val episodesDtoByChapter: List<EpisodeDto> = dao.getEpisodesByChapter(chapter)
            val episodesByChapter: List<Episode> = episodesDtoByChapter.map { it.toEpisode() }

            fusionSourceDB(episodesByChapter)
        }
    }

    // _____________________________ DE LA BASE DE DATOS _____________________________
    override suspend fun getAllEpisodesDb(): List<Episode> {
        return withContext(Dispatchers.IO) {
            val episodesDb: List<Episode> = databaseDao.getAllEpisodesDb()
            episodesDb
        }
    }

    override suspend fun getEpisodeByIdDb(id: String): Episode? {
        return withContext(Dispatchers.IO) {
            val episodeDb: Episode? = databaseDao.getEpisodeByIdDb(id)
            episodeDb
        }
    }

    override suspend fun getEpisodeByIdsDb(ids: List<String>): List<Episode>? {
        return withContext(Dispatchers.IO) {
            val episodesDb: List<Episode>? = databaseDao.getEpisodeByIdsDb(ids)
            episodesDb
        }
    }

    override suspend fun updateEpisodeDb(id: String, isView: Boolean, isFav: Boolean) {
        return withContext(Dispatchers.IO) {
            databaseDao.updateEpisodeDb(id, isView, isFav)
        }
    }

    override suspend fun insertEpisodeDb(episode: Episode, isView: Boolean, isFav: Boolean) {
        return withContext(Dispatchers.IO) {
            databaseDao.insertEpisodeDb(episode, isView, isFav)
        }
    }
}