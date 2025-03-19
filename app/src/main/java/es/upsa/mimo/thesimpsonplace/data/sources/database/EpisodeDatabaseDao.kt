package es.upsa.mimo.thesimpsonplace.data.sources.database

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

interface EpisodeDatabaseDao {
    fun getAllEpisodesDb(): List<Episode>
    fun getEpisodeByIdDb(id: String): Episode?
    fun getEpisodeByIdsDb(ids: List<String>): List<Episode>?
    fun updateEpisodeDb(id: String, isView: Boolean, isFav: Boolean): Unit
    fun insertEpisodeDb(episode: Episode, isView: Boolean, isFav: Boolean): Unit
}


