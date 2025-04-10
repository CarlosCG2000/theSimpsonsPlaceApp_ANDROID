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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MockModule {

    // Esta clase simula la API y devuelve las citas desde un JSON de test.
    @Provides
    @Singleton
    fun provideQuoteDao(@ApplicationContext context: Context): QuoteDao {
        val json = context.assets.open("citas_test.json").bufferedReader().use { it.readText() }
        val citas = Gson().fromJson(json, Array<QuoteDTO>::class.java).toList()
        return object : QuoteDao {
            override suspend fun getQuotes(
                numElementos: Int,
                textPersonaje: String
            ): List<QuoteDTO> {
                val filtradas = if (textPersonaje.isEmpty()) {
                    citas
                } else {
                    citas.filter { it.personaje?.contains(textPersonaje, ignoreCase = true) == true }
                }

                return filtradas.shuffled().take(numElementos)
            }
        }
    }
}