package es.upsa.mimo.thesimpsonplace.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upsa.mimo.thesimpsonplace.data.daos.remote.QuoteDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton // ✅ Solo necesitamos una instancia de Retrofit
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://thesimpsonsquoteapi.glitch.me/")
            .addConverterFactory(GsonConverterFactory.create()) // ✅ Usa Gson
            .build()
    }

    @Provides
    @Singleton
    fun provideQuoteDao(retrofit: Retrofit): QuoteDao {
        return retrofit.create(QuoteDao::class.java) // ✅ Retrofit ya implementa la interfaz automáticamente - No añado 'QuoteDaoApi'
    }
}

/**
    NetworkModule.kt (Retrofit y API remota)
    Este módulo gestiona la configuración de Retrofit para interactuar con la API externa.

    ✔ Funciones @Provides
    •	provideRetrofit() → Instancia única de Retrofit.
    •	provideQuoteDao() → Crea QuoteDao con retrofit.create(QuoteDao::class.java).

    Nota: No es necesario crear un QuoteDaoImpl, ya que Retrofit implementa la interfaz automáticamente.
 */
