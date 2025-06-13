package es.upsa.mimo.thesimpsonplace.data.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.upsa.mimo.thesimpsonplace.data.daos.remote.QuoteDao
import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteDTO
import es.upsa.mimo.thesimpsonplace.utils.Logger
import javax.inject.Singleton

/**
    MockModule.kt (Mock de API ficticio para pruebas)
    Este módulo proporciona una implementación falsa de QuoteDao para pruebas.
 */
@Module
@InstallIn(SingletonComponent::class)
object MockModule: Logger {

    // Esta clase simula la API y devuelve las citas desde un JSON de test.
    @Provides
    @Singleton
    fun provideQuoteDao(@ApplicationContext context: Context): QuoteDao {
        val json = context.assets.open("citas_test.json").bufferedReader().use { it.readText() } // Se lee el contenido del archivo 'citas_test.json' al iniciar la app.
        val citas = Gson().fromJson(json, Array<QuoteDTO>::class.java).toList() // El contenido se parsea con Gson a una lista de QuoteDTO.

        return object : QuoteDao {
            // getQuotes() filtra y devuelve citas aleatorias, replicando el comportamiento esperado de la API pero sin necesidad de conexión a Internet.
            override suspend fun getQuotes(numElementos: Int, textPersonaje: String): List<QuoteDTO> {
                logInfo("MockModule: ${citas.size} citas cargadas desde el JSON de test") // Para ver en el LogCat que se crea la instancia de Retrofit

                val filtradas =
                    if (textPersonaje.isEmpty()) citas
                    else citas.filter { it.personaje?.contains(textPersonaje, ignoreCase = true) == true }

                return filtradas.shuffled().take(numElementos)
            }
        }
    }
}