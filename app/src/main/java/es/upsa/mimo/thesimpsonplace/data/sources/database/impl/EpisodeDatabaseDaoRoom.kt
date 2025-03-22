package es.upsa.mimo.thesimpsonplace.data.sources.database.impl

import es.upsa.mimo.thesimpsonplace.data.sources.database.EpisodeDatabaseDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

class EpisodeDatabaseDaoRoom: EpisodeDatabaseDao {
    override suspend fun getAllEpisodesDb(): List<Episode> {
        TODO("Not yet implemented")
    }

    override suspend fun getEpisodeByIdDb(id: String): Episode? {
        TODO("Not yet implemented")
    }

    override suspend fun getEpisodeByIdsDb(ids: List<String>): List<Episode>? {
        TODO("Not yet implemented")
    }

    override suspend fun updateEpisodeDb(id: String, esView: Boolean, isFav: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun insertEpisodeDb(
        episode: Episode,
        isView: Boolean,
        isFav: Boolean
    ) {
        TODO("Not yet implemented")
    }
}