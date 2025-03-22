package es.upsa.mimo.thesimpsonplace.data.sources.database

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

interface EpisodeDatabaseDao {
    suspend fun getAllEpisodesDb(): List<Episode>
    suspend fun getEpisodeByIdDb(id: String): Episode?
    suspend fun getEpisodeByIdsDb(ids: List<String>): List<Episode>?
    suspend fun updateEpisodeDb(id: String, isView: Boolean, isFav: Boolean): Unit
    suspend fun insertEpisodeDb(episode: Episode, isView: Boolean, isFav: Boolean): Unit
}


