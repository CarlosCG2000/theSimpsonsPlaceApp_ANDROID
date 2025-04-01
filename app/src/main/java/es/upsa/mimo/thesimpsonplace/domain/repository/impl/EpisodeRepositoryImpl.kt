package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.mappers.toEpisode
import es.upsa.mimo.thesimpsonplace.data.daos.local.room.EpisodeDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.daos.remote.EpisodeDao
import es.upsa.mimo.thesimpsonplace.domain.models.Episode
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
                                                private val databaseDao: EpisodeDatabaseDao): EpisodeRepository {

/**
    suspend fun fusionSourceDB(episodesSource: List<Episode>): List<Episode> {
        return withContext(Dispatchers.IO) {
            val ids: List<String> = episodesSource.map { it.id } // Se optienen los ids de todos los episodios

            // 🚀 2️⃣ Cargar datos de la BD
            val episodesFromDb: Map<String, Episode> = databaseDao.getEpisodeByIdsDb(ids) // si hay episodios en la base de datos con dichos ids se recogen
                ?.associateBy { it.id } ?: emptyMap()

            // 🚀 3️⃣️ Resultado final de los datos
             episodesSource.map { episode ->
                val episodeDb = episodesFromDb(episode.id])

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

    // _____________________________ Mock Json o Api _____________________________
    override suspend fun getAllEpisodes(): List<Episode> =
         withContext(Dispatchers.IO) {
            // Cargar datos del JSON/API
            dao.getAllEpisodes().map { it.toEpisode() } // Se mapean a episodios para utilizar en las vistas
        }

    override suspend fun getEpisodeById(id: String): Episode? =
        withContext(Dispatchers.IO) {
            // Cargar datos del JSON/API
            dao.getEpisodeById(id = id)?.toEpisode()
        }

    override suspend fun getEpisodesByTitle(title: String,
                                            episodes: List<Episode>): List<Episode> =
        withContext(Dispatchers.IO) {
            // Cargar datos del JSON/API
            val episodesFilter: List<Episode> = dao.getEpisodesByTitle(title).map { it.toEpisode() }

            if (episodesFilter == emptyList<Episode>())
                emptyList<Episode>()
            else {
                val episodeIdsFilter = episodesFilter.map { it.id }.toSet()
                episodes.filter { episodeIdsFilter.contains(it.id) == true }
            }
        }

    override suspend fun getEpisodesByDate(minDate: Date?, maxDate: Date?,
                                           episodes: List<Episode>): List<Episode> =
         withContext(Dispatchers.IO) {
            val episodesFilter: List<Episode> = dao.getEpisodesByDate(minDate, maxDate).map { it.toEpisode() }

            if (episodesFilter == emptyList<Episode>()){
                emptyList<Episode>()
            } else {
                val episodeIdsFilter = episodesFilter.map { it.id }.toSet()
                episodes.filter { episodeIdsFilter.contains(it.id) == true }
            }
        }

    override suspend fun getEpisodesBySeason(season: Int,
                                             episodes: List<Episode>): List<Episode> =
         withContext(Dispatchers.IO) {
            val episodesFilter: List<Episode> = dao.getEpisodesBySeason(season).map { it.toEpisode() }

            if (episodesFilter == emptyList<Episode>()){
                emptyList<Episode>()
            } else {
                val episodeIdsFilter = episodesFilter.map { it.id }.toSet()
                episodes.filter { episodeIdsFilter.contains(it.id) == true }
            }
        }

    override suspend fun getEpisodesByChapter(chapter: Int,
                                              episodes: List<Episode>): List<Episode> =
         withContext(Dispatchers.IO) {
            val episodesFilter: List<Episode> = dao.getEpisodesByChapter(chapter).map { it.toEpisode() }

            if (episodesFilter == emptyList<Episode>()){
                emptyList<Episode>()
            } else {
                val episodeIdsFilter = episodesFilter.map { it.id }.toSet()
                episodes.filter { episodeIdsFilter.contains(it.id) == true }
            }
        }

    // (2) Necesitamos tratar 'Episode' y no 'EpisodeDto' por eso esta lógica se encuentra aquí (Repository) y no en el Dao
    override suspend fun getEpisodesByView(isView: Boolean,
                                           episodes: List<Episode>): List<Episode> =
         withContext(Dispatchers.IO) {

            val listEpisodeFav = getWatchedEpisodes().firstOrNull()

            if (listEpisodeFav != null) {
                val episodeIdsInDB = listEpisodeFav.map { it.id }.toSet()
                if (isView) episodes.filter { episodeIdsInDB.contains(it.id) == true }
                else        episodes.filter { episodeIdsInDB.contains(it.id) == false }
            } else {
                if (isView) emptyList<Episode>()
                else        episodes
            }
        }

    override suspend fun getEpisodesOrder(isAscendent: Boolean,
                                          episodes: List<Episode>): List<Episode> =
         if (isAscendent) episodes.sortedBy { it.lanzamiento }
         else episodes.sortedByDescending { it.lanzamiento }

    // _____________________________ Base de datos Room  _____________________________
    override fun getAllEpisodesDb(): Flow<List<Episode>> =
        databaseDao.getAllEpisodesDb().map { list ->
            list.map { it.toEpisode() }
        }

    override suspend fun getEpisodeDbById(episodeId: String): Episode? =
        databaseDao.getEpisodeDbById(episodeId)?.toEpisode()

    override fun getWatchedEpisodes(): Flow<List<Episode>> =
        databaseDao.getWatchedEpisodes().map { list ->
            list.map { it.toEpisode() }
        }

    override suspend fun isEpisodeDbWatched(episodeId: String): Boolean? =
         databaseDao.isEpisodeDbWatched(episodeId)

    override suspend fun isEpisodeDbFavorite(episodeId: String): Boolean? =
         databaseDao.isEpisodeDbFavorite(episodeId)

    override suspend fun insertEpisodeDb(episode: Episode) =
         databaseDao.insertEpisodeDb(episode.toEpisodeDb(isFav = episode.esFavorito, isView = episode.esVisto))

    override suspend fun updateEpisodeDbStatus( episodeId: String,
                                                esVisto: Boolean, // su nuevo 'esVisto'
                                                esFavorito: Boolean // su nuevo 'esFavorito'
    ) = databaseDao.updateEpisodeDbStatus(episodeId = episodeId, esVisto = esVisto, esFavorito = esFavorito)
}
