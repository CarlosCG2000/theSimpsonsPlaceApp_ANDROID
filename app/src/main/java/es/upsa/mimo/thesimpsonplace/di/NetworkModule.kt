package es.upsa.mimo.thesimpsonplace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upsa.mimo.thesimpsonplace.data.sources.service.QuoteDao
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