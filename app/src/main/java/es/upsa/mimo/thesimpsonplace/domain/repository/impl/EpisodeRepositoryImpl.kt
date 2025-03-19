package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDto
import es.upsa.mimo.thesimpsonplace.data.mappers.toEpisode
import es.upsa.mimo.thesimpsonplace.data.sources.database.EpisodeDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.EpisodeDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import java.util.Date

class EpisodeRepositoryImpl(val dao: EpisodeDao, val databaseDao: EpisodeDatabaseDao): EpisodeRepository {

    fun fusionSourceDB(episodesSource: List<Episode>): List<Episode> {
        val ids: List<String> = episodesSource.map { it.id } // Se optienen los ids de todos los episodios

        // 🚀 2️⃣ Cargar datos de la BD
        val episodesFromDb: Map<String, Episode> = databaseDao.getEpisodeByIdsDb(ids) // si hay episodios en la base de datos con dichos ids se recogen
            ?.associateBy { it.id } ?: emptyMap()

        // 🚀 3️⃣️ Resultado final de los datos
        return episodesSource.map { episode ->
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

    // _____________________________ DEL MOCK JSON O API _____________________________
    override fun getAllEpisodes(): List<Episode> {
        // 🚀 1️⃣ Cargar datos del JSON/API
        val allEpisodesDto: List<EpisodeDto> = dao.getAllEpisodes() // Se obtienen todos los episodios de la funete de datos (mock Json, Api, etc)
        val allEpisodes: List<Episode> = allEpisodesDto.map { it.toEpisode() } // Se mapean a episodios para utilizar en las vistas

        return fusionSourceDB(allEpisodes)
    }

    override fun getEpisodeById(id: String): Episode? {
        // 🚀 1️⃣ Cargar datos del JSON/API
        val episodeDto: EpisodeDto? = dao.getEpisodeById(id = id)
        val episode: Episode? = episodeDto?.toEpisode()

        // 🚀 2️⃣ Cargar datos de la BD
        val episodeDB: Episode? = databaseDao.getEpisodeByIdDb(id)

        // 🚀 3️⃣️ Resultado final de los datos
        if (episode != null && episodeDB != null){
             return episode.copy(
                esFavorito = episodeDB.esFavorito,
                esVisto = episodeDB.esVisto
            )
        }

        return episode
    }

    override fun getEpisodesByTitle(title: String): List<Episode> {
        // 🚀 1️⃣ Cargar datos del JSON/API
        val episodesDtoByTitle: List<EpisodeDto> = dao.getEpisodesByTitle(title)
        val episodesByTitle: List<Episode> = episodesDtoByTitle.map { it.toEpisode() }

        return fusionSourceDB(episodesByTitle)
    }

    override fun getEpisodesByDate(minDate: Date?, maxDate: Date?): List<Episode> {
        val episodesDtoByDate: List<EpisodeDto> = dao.getEpisodesByDate(minDate, maxDate)
        val episodesByDate: List<Episode> = episodesDtoByDate.map { it.toEpisode() }

        return fusionSourceDB(episodesByDate)
    }

    override fun getEpisodesBySeason(season: Int): List<Episode> {
        val episodesDtoBySeason: List<EpisodeDto> = dao.getEpisodesBySeason(season)
        val episodesBySeason: List<Episode> = episodesDtoBySeason.map { it.toEpisode() }

        return fusionSourceDB(episodesBySeason)
    }

    override fun getEpisodesByChapter(chapter: Int): List<Episode> {
        val episodesDtoByChapter: List<EpisodeDto> = dao.getEpisodesByChapter(chapter)
        val episodesByChapter: List<Episode> = episodesDtoByChapter.map { it.toEpisode() }

        return fusionSourceDB(episodesByChapter)
    }

    // _____________________________ DE LA BASE DE DATOS _____________________________
    override fun getAllEpisodesDb(): List<Episode> {
        val episodesDb: List<Episode> = databaseDao.getAllEpisodesDb()
        return episodesDb
    }

    override fun getEpisodeByIdDb(id: String): Episode? {
        val episodeDb: Episode? = databaseDao.getEpisodeByIdDb(id)
        return episodeDb
    }

    override fun getEpisodeByIdsDb(ids: List<String>): List<Episode>? {
        val episodesDb: List<Episode>? = databaseDao.getEpisodeByIdsDb(ids)
        return episodesDb
    }

    override fun updateEpisodeDb(id: String, isView: Boolean, isFav: Boolean) {
        databaseDao.updateEpisodeDb(id, isView, isFav)
    }

    override fun insertEpisodeDb(episode: Episode, isView: Boolean, isFav: Boolean) {
        databaseDao.insertEpisodeDb(episode, isView, isFav)
    }
}