package es.upsa.mimo.thesimpsonplace.data.di















// DOS VERSIONES MANEJADAS POR FLAVORS EN EL 'GRADLE.KTS' --> 'REMOTE' O 'MOCK' -> ESTILO DE CARPETAS PROJECT -> REMOTE [MAIN] O MOCK [MAIN]













/**
    NetworkModule.kt (Retrofit y API remota)
    Este módulo gestiona la configuración de Retrofit para interactuar con la API externa.

    ✔ Funciones @Provides
    •	provideRetrofit() → Instancia única de Retrofit.
    •	provideQuoteDao() → Crea QuoteDao con retrofit.create(QuoteDao::class.java).

    Nota: No es necesario crear un QuoteDaoImpl, ya que Retrofit implementa la interfaz automáticamente.
 */
/**
// Esta es la instancia de RetroFit
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule { // Object ¡IMPORTANTE! Necesitamos una unica estancia para todas las conexiones
    private const val BASE_URL = "https://thesimpsonsquoteapi.glitch.me/" // Llamamos a la raiz del servidor (ApiRest)

    // Creamos un objeto de tipo Retrofit
    @Provides
    @Singleton // Solo necesitamos una instancia de Retrofit
    fun provideRetrofit(): Retrofit {
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
*/