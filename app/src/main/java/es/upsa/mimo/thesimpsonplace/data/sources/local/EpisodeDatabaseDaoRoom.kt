package es.upsa.mimo.thesimpsonplace.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDatabaseDaoRoom {
    // 1️⃣ Obtener todos los episodios de la BD
    @Query("SELECT * FROM episodes")
    fun getAllEpisodesDb(): Flow<List<EpisodeEntity>>

    // 2️⃣ Obtener si un episodio existe en la BD
    @Query("SELECT * FROM episodes WHERE id = :episodeId LIMIT 1")
    suspend fun getEpisodeDbById(episodeId: String): EpisodeEntity?

    // 3️⃣ Obtener solo los episodios que han sido marcados como vistos
    @Query("SELECT * FROM episodes WHERE esVisto = 1")
    fun getWatchedEpisodes(): Flow<List<EpisodeEntity>>

    // 4️⃣ Comprobar si un episodio está marcado como visto
    @Query("SELECT esVisto FROM episodes WHERE id = :episodeId")
    suspend fun isEpisodeDbWatched(episodeId: String): Boolean?

    // 5️⃣ Comprobar si un episodio está marcado como favorito
    @Query("SELECT esFavorito FROM episodes WHERE id = :episodeId")
    suspend fun isEpisodeDbFavorite(episodeId: String): Boolean?

    // 6️⃣ Insertar un episodio en la BD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodeDb(episode: EpisodeEntity)

    // 7️⃣ Modificar los campos `esVisto` y `esFavorito` de un episodio
    @Query("UPDATE episodes SET esVisto = :esVisto, esFavorito = :esFavorito WHERE id = :episodeId")
    suspend fun updateEpisodeDbStatus(episodeId: String, esVisto: Boolean, esFavorito: Boolean)
}