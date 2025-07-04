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

/**
    NetworkModule.kt (Retrofit y API remota)
    Este módulo gestiona la configuración de Retrofit para interactuar con la API externa.

    ✔ Funciones @Provides
        • provideRetrofit() → Instancia única de Retrofit.
        • provideQuoteDao() → Crea QuoteDao con retrofit.create(QuoteDao::class.java).

    Nota: No es necesario crear un QuoteDaoImpl, ya que Retrofit implementa la interfaz automáticamente.
*/
// @Module y @InstallIn: para que las dependencias estén disponibles a nivel global
@Module
@InstallIn(SingletonComponent::class)
// Object ¡IMPORTANTE! Necesitamos una unica estancia para todas las conexiones
object NetworkModule: Logger {
    private const val BASE_URL = "https://thesimpsonsquoteapi.glitch.me/" // Llamamos a la raiz del servidor (ApiRest)

    // Creamos un objeto de tipo Retrofit
    @Provides
    @Singleton // Solo necesitamos una instancia de Retrofit
    fun provideRetrofit(): Retrofit {
        logInfo("Creando la instancia de Retrofit $BASE_URL")   // Para ver en el LogCat que se crea la instancia de Retrofit
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())                       // OkHttpClient: interceptor de logging para ver los detalles de las peticiones HTTP en el Logcat
            .addConverterFactory(GsonConverterFactory.create()) // Usa Gson para convertir de json a nuestros objetos 'QuoteEntity'
            .build()
    }

    @Provides
    @Singleton
    fun provideQuoteDao(retrofit: Retrofit): QuoteDao {     // Inyección del QuoteDao: interfaz que define los métodos para acceder a la API
        return retrofit.create( QuoteDao::class.java)       // Retrofit ya implementa la interfaz automáticamente - No añado 'QuoteDaoApi'
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