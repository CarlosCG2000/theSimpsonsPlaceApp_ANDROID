package es.upsa.mimo.thesimpsonplace.data.sources.database.impl

import es.upsa.mimo.thesimpsonplace.data.sources.database.QuoteDatabaseDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

class QuoteDatabaseDaoRoom: QuoteDatabaseDao {

//  Room simplifica el acceso a la base de datos.
//  Instala la dependencia en build.gradle.kts:
    /**
    dependencies {
        implementation("androidx.room:room-runtime:2.5.2")
        kapt("androidx.room:room-compiler:2.5.2") // KAPT para generar código de Room
    }
    */

//  Implementación en QuoteDatabaseDao:
    /**
    import androidx.room.*

    @Dao
    interface QuoteDatabaseDao {
        @Query("SELECT * FROM quote")
        fun getAllQuoteDb(): List<Quote>

        @Query("SELECT * FROM quote WHERE cita = :cita")
        fun getQuoteByCitaDb(cita: String): Quote?

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertQuoteDb(quote: Quote)

        @Query("DELETE FROM quote WHERE cita = :cita")
        fun deleteQuoteDb(cita: String)
    }
    */

//    ✅ Explicación:
//    •	@Query permite ejecutar SQL en Room.
//    •	@Insert(onConflict = OnConflictStrategy.REPLACE) previene duplicados.
//    •	@Dao convierte la interfaz en un acceso a la BD.

    override suspend fun getAllQuoteDb(): List<Quote> {
        TODO("Not yet implemented")
    }

    override suspend fun getQuoteByCitaDb(cita: String): Quote {
        TODO("Not yet implemented")
    }

    override suspend fun insertQuoteDb(quote: Quote) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteQuoteDb(cita: String) {
        TODO("Not yet implemented")
    }
}