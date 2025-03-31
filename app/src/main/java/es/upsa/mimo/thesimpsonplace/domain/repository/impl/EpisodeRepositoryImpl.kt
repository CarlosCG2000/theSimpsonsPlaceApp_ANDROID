package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.mappers.toEpisode
import es.upsa.mimo.thesimpsonplace.data.sources.local.EpisodeDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.remote.EpisodeDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.mappers.toEpisodeDb
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
import kotlin.collections.map

class EpisodeRepositoryImpl @Inject constructor(val dao: EpisodeDao,
                                                private val databaseDao: EpisodeDatabaseDaoRoom): EpisodeRepository {

/**
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
*/

    // _____________________________ DEL MOCK JSON O API _____________________________
    override suspend fun getAllEpisodes(): List<Episode> {
        return withContext(Dispatchers.IO) {
            // 🚀 1️⃣ Cargar datos del JSON/API
            dao.getAllEpisodes().map { it.toEpisode() } // Se mapean a episodios para utilizar en las vistas

            // fusionSourceDB(allEpisodes)
        }
    }

    override suspend fun getEpisodeById(id: String): Episode? {
        return withContext(Dispatchers.IO) {
            // 🚀 1️⃣ Cargar datos del JSON/API
            dao.getEpisodeById(id = id)?.toEpisode()

//            // 🚀 2️⃣ Cargar datos de la BD
//            val episodeDB: Episode? = databaseDao.getEpisodeByIdDb(id)
//
//            // 🚀 3️⃣️ Resultado final de los datos
//            if (episode != null && episodeDB != null){
//                  episode.copy(
//                    esFavorito = episodeDB.esFavorito,
//                    esVisto = episodeDB.esVisto
//                )
//            } else{
//                episode
//            }

        }
    }

    override suspend fun getEpisodesByTitle(title: String,
                                            episodes: List<Episode>): List<Episode> {
        return withContext(Dispatchers.IO) {

            // 🚀 1️⃣ Cargar datos del JSON/API
            val episodesFilter: List<Episode> = dao.getEpisodesByTitle(title).map { it.toEpisode() }

            if (episodesFilter == emptyList<Episode>()){
                episodes
            } else {
                val episodeIdsFilter = episodesFilter.map { it.id }.toSet()
                episodes.filter { episodeIdsFilter.contains(it.id) == true }
            }
//            // 🚀 2️⃣ Filtrar solo los episodios que están en ambas listas
//            val filteredEpisodes = episodesByTitle.filter { it in episodes }
//
//            fusionSourceDB(if(episodes.isEmpty()) episodesByTitle else filteredEpisodes)
        }
    }

    override suspend fun getEpisodesByDate(minDate: Date?, maxDate: Date?,
                                           episodes: List<Episode>): List<Episode> {
        return withContext(Dispatchers.IO) {
            val episodesFilter: List<Episode> = dao.getEpisodesByDate(minDate, maxDate).map { it.toEpisode() }

            if (episodesFilter == emptyList<Episode>()){
                episodes
            } else {
                val episodeIdsFilter = episodesFilter.map { it.id }.toSet()
                episodes.filter { episodeIdsFilter.contains(it.id) == true }
            }
//            // 🚀 2️⃣ Filtrar solo los episodios que están en ambas listas
//            val filteredEpisodes = episodesByDate.filter { it in episodes }
//
//            fusionSourceDB(if(episodes.isEmpty()) episodesByDate else filteredEpisodes)
        }
    }

    override suspend fun getEpisodesBySeason(season: Int,
                                             episodes: List<Episode>): List<Episode> {
        return withContext(Dispatchers.IO) {
            val episodesFilter: List<Episode> = dao.getEpisodesBySeason(season).map { it.toEpisode() }
            // val filteredEpisodes = episodesBySeason.filter { it in episodes }

            if (episodesFilter == emptyList<Episode>()){
                episodes
            } else {
                val episodeIdsFilter = episodesFilter.map { it.id }.toSet()
                episodes.filter { episodeIdsFilter.contains(it.id) == true }
            }
            // fusionSourceDB(if(episodes.isEmpty()) episodesBySeason else filteredEpisodes)
        }
    }

    override suspend fun getEpisodesByChapter(chapter: Int,
                                              episodes: List<Episode>): List<Episode> {
        return withContext(Dispatchers.IO) {
            val episodesFilter: List<Episode> = dao.getEpisodesByChapter(chapter).map { it.toEpisode() }

            if (episodesFilter == emptyList<Episode>()){
                episodes
            } else {
                val episodeIdsFilter = episodesFilter.map { it.id }.toSet()
                episodes.filter { episodeIdsFilter.contains(it.id) == true }
            }

            // fusionSourceDB(if(episodes.isEmpty()) episodesByChapter else filteredEpisodes)
        }
    }

    // (2) Necesitamos tratar 'Episode' y no 'EpisodeDto' por eso esta lógica se encuentra aquí (Repository) y no en el Dao
    override suspend fun getEpisodesByView(isView: Boolean,
                                           episodes: List<Episode>): List<Episode> {
        return withContext(Dispatchers.IO) {

            val listEpisodeFav = getWatchedEpisodes().firstOrNull()

            if (listEpisodeFav != null) {
                val episodeIdsInDB = listEpisodeFav.map { it.id }.toSet()
                if (isView) episodes.filter { episodeIdsInDB.contains(it.id) == true }
                else        episodes.filter { episodeIdsInDB.contains(it.id) == false }
            } else {
                if (isView) emptyList()
                else        episodes
            }

        }
    }

    override suspend fun getEpisodesOrder(isAscendent: Boolean,
                                          episodes: List<Episode>): List<Episode> {
        return if (isAscendent) episodes.sortedBy { it.lanzamiento }
               else episodes.sortedByDescending { it.lanzamiento }
    }

    // _____________________________ ROOM - BASE DE DATOS _____________________________
    override fun getAllEpisodesDb(): Flow<List<Episode>> {
        return databaseDao.getAllEpisodesDb().map { list ->
            list.map { it.toEpisode() }
        }
    }

    override suspend fun getEpisodeDbById(episodeId: String): Episode? {
        return databaseDao.getEpisodeDbById(episodeId)?.toEpisode()
    }

    override fun getWatchedEpisodes(): Flow<List<Episode>> {
        return databaseDao.getWatchedEpisodes().map { list ->
            list.map { it.toEpisode() }
        }
    }

    override suspend fun isEpisodeDbWatched(episodeId: String): Boolean? {
        return databaseDao.isEpisodeDbWatched(episodeId)
    }

    override suspend fun isEpisodeDbFavorite(episodeId: String): Boolean? {
        return databaseDao.isEpisodeDbFavorite(episodeId)
    }

    override suspend fun insertEpisodeDb(episode: Episode) { // OJO MODIFCAR EL EPISODIO QUE SE PASE CON SU VIEW Y FAVBORITE CORRESPONDIENTE AL AÑADIRSE
         databaseDao.insertEpisodeDb(episode.toEpisodeDb(isFav = episode.esFavorito, isView = episode.esVisto))
    }

    override suspend fun updateEpisodeDbStatus(
        episodeId: String,
        esVisto: Boolean, // SU NUEVO 'esVisto'
        esFavorito: Boolean // SU NUEVO 'esFavorito'
    ) {
        databaseDao.updateEpisodeDbStatus(episodeId = episodeId, esVisto = esVisto, esFavorito = esFavorito)
    }

}