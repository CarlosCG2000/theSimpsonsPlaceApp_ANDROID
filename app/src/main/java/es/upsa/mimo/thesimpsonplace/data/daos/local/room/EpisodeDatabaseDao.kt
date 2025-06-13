package es.upsa.mimo.thesimpsonplace.data.daos.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDatabaseDao {
    // Obtener todos los episodios de la BD
    @Query("""  SELECT * 
                FROM ${EpisodeEntity.TABLE_NAME}
           """)
    fun getAllEpisodesDb(): Flow<List<EpisodeEntity>>

    // Obtener si un episodio existe en la BD
    @Query("""  SELECT * 
                FROM ${EpisodeEntity.TABLE_NAME} 
                WHERE id = :episodeId 
                LIMIT 1
           """)
    suspend fun getEpisodeDbById(episodeId: String): EpisodeEntity?

    // Obtener solo los episodios que han sido marcados como vistos
    @Query("""  SELECT * 
                FROM ${EpisodeEntity.TABLE_NAME} 
                WHERE esVisto = 1
           """)
    fun getWatchedEpisodes(): Flow<List<EpisodeEntity>>

    // Comprobar si un episodio está marcado como visto
    @Query("""  SELECT esVisto 
                FROM ${EpisodeEntity.TABLE_NAME} 
                WHERE id = :episodeId
                LIMIT 1
            """)
    suspend fun isEpisodeDbWatched(episodeId: String): Boolean?

    // Comprobar si un episodio está marcado como favorito
    @Query("""  SELECT esFavorito 
                FROM ${EpisodeEntity.TABLE_NAME} 
                WHERE id = :episodeId
           """)
    suspend fun isEpisodeDbFavorite(episodeId: String): Boolean?

    // Modificar los campos `esVisto` y `esFavorito` de un episodio
    @Query("""  UPDATE ${EpisodeEntity.TABLE_NAME}
                SET esVisto = :esVisto, esFavorito = :esFavorito 
                WHERE id = :episodeId
           """)
    suspend fun updateEpisodeDbStatus(episodeId: String, esVisto: Boolean, esFavorito: Boolean)

    // Insertar un episodio en la BD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodeDb(episode: EpisodeEntity)

}