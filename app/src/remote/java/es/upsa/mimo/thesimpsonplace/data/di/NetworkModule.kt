package es.upsa.mimo.thesimpsonplace.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upsa.mimo.thesimpsonplace.data.daos.remote.QuoteDao
import es.upsa.mimo.thesimpsonplace.utils.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Esta es la instancia de RetroFit
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule: Logger { // Object ¡IMPORTANTE! Necesitamos una unica estancia para todas las conexiones
    private const val BASE_URL = "https://thesimpsonsquoteapi.glitch.me/" // Llamamos a la raiz del servidor (ApiRest)

    // Creamos un objeto de tipo Retrofit
    @Provides
    @Singleton // Solo necesitamos una instancia de Retrofit
    fun provideRetrofit(): Retrofit {
        logInfo("Creando la instancia de Retrofit $BASE_URL") // Para ver en el LogCat que se crea la instancia de Retrofit
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient()) // Cuando ejecutemos las peticiones veremos en el LogCat el resultado
            .addConverterFactory(GsonConverterFactory.create()) // Usa Gson para convertir de json a nuestros objetos 'QuoteEntity'
            .build()
    }

    @Provides
    @Singleton
    fun provideQuoteDao(retrofit: Retrofit): QuoteDao {
        return retrofit.create( QuoteDao::class.java) // Retrofit ya implementa la interfaz automáticamente - No añado 'QuoteDaoApi'
    }

    /** Podemos crear una instancia de Http con ciertas condiciones para verlo en el LogCat al hacer las llamadas */
    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//          .connectTimeout(30, TimeUnit.SECONDS)
//          .readTimeout(30, TimeUnit.SECONDS)
//          .writeTimeout(30, TimeUnit.SECONDS)
            .build()
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
