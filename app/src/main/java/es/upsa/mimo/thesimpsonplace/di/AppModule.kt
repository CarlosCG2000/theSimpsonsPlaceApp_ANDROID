package es.upsa.mimo.thesimpsonplace.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.upsa.mimo.thesimpsonplace.data.sources.database.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.database.EpisodeDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.CharacterDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.EpisodeDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.QuoteDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.service.CharacterDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.EpisodeDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.QuoteDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.impl.CharacterDaoJson
import es.upsa.mimo.thesimpsonplace.data.sources.service.impl.EpisodeDaoJson
import es.upsa.mimo.thesimpsonplace.data.sources.service.impl.QuoteDaoApi
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.repository.impl.CharaterRepositoryImpl
import es.upsa.mimo.thesimpsonplace.domain.repository.impl.EpisodeRepositoryImpl
import es.upsa.mimo.thesimpsonplace.domain.repository.impl.QuoteRepositoryImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.FetchAllCharactersDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetFilterNameCharactersUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.InsertCharacterDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.UpdateCharacterDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeByIdDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeByIdUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeByIdsDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByChapterUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByDateUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesBySeasonUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByTitleUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.InsertEpisodeDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.UpdateEpisodeDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.FetchAllCharactersDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.GetAllCharactersUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.GetFilterNameCharactersUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.InsertCharacterDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.UpdateCharacterDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetAllEpisodesDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetAllEpisodesUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodeByIdDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodeByIdUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodeByIdsDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodesByChapterUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodesByDateUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodesBySeasonUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodesByTitleUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.InsertEpisodeDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.UpdateEpisodeDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote.DeleteQuoteDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote.GetAllQuoteDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote.GetQuestionsUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote.GetQuotesUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote.InsertQuoteDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.DeleteQuoteDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetAllQuoteDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuestionsUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuotesUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.InsertQuoteDbUseCase
import javax.inject.Singleton


// AppModule maneja repositorios, casos de uso y lógica de negocio.
@Module // Para proporcionar las dependencias
@InstallIn(SingletonComponent::class)
object AppModule {
/**
 * 📌 ¿Por qué te funciona igual con o sin @Inject constructor?
 *
 * El motivo por el que tu código funciona igual con o sin @Inject constructor es porque estás usando @Provides en el AppModule. Esto significa que Hilt no necesita inyectar automáticamente las dependencias en tus clases, ya que tú mismo las estás proporcionando manualmente en el módulo.
 *
 * 🛠 Diferencia clave entre @Inject constructor y @Provides
 *
 * ✅ Si usas @Provides en el módulo, no es necesario @Inject constructor en las clases porque ya estás diciéndole manualmente a Hilt cómo crear cada instancia.
 *
 * ✅ Si usas @Inject constructor en las clases, entonces no necesitas @Provides en el módulo (excepto si necesitas lógica de inicialización personalizada, como en CharacterDaoJson).
 *
 * 👉 ¿Cuándo elegir una opción u otra?
 * 	•	Dejar @Provides en AppModule si en el futuro necesitas cambiar la implementación fácilmente.
 * 	•	Eliminar @Provides y solo usar @Inject constructor si la implementación no va a cambiar nunca y prefieres menos código en AppModule.
 */

//   Ahora Hilt puede inyectarlos automáticamente al detectar @Inject constructor() en sus clases.
    @Provides // Esto hace que Hilt nunca use @Inject constructor de CharacterDaoJson, porque ya le estás diciendo exactamente cómo crear la instancia. Aquí Hilt sabe que cuando alguien necesite CharaterDao, debe proporcionar una instancia de CharaterDaoImpl. Al tener la eleccion de json de test o produccion, lo implementamos manualmente pasandole el @Provides y luego la eplciaicon de dentro
    // ¿PORQUE @Provides? La implemención de dicho Dao no tiene' @Inject constructor' (no es automatizada), debido a que quiero proveerlo (con @Provides) de forma más manual al elegir yo si el dao de implementacioón sea para json de test o producción cambiando el parámetro recibido
    @Singleton // ✅ Solo debe haber una instancia de CharacterDao en toda la app
    fun provideCharacterDao(@ApplicationContext context: Context): CharacterDao {
        val test = false // Cambia a `true` o 'false' para usar d elos json de test a producción

        return if (test) {
            CharacterDaoJson(context, "personajes_test.json", "imagenes_test.json")
        } else {
            CharacterDaoJson(context, "personajes_data.json", "imagenes_data.json")
        }
    }

    // CharacterDatabaDao --> DatabaseModule.kt

    @Provides
    @Singleton
    fun provideEpisodeDao(@ApplicationContext context: Context): EpisodeDao {
        val test = true

        return if(test) {
            EpisodeDaoJson(context, "episodios_test.json")
        }else {
            EpisodeDaoJson(context, "episodios_data.json")
        }

    }

    // EpisodeDatabaDao --> DatabaseModule.k

    // Por si quisiera cambiar de API seria aqui simplemente (si quiero pasarselo por parametro debo de cambiar mi implementación de QuoteDaoApi()
    @Provides
    @Singleton
    fun provideQuoteDao(): QuoteDao {
        // XXXXX // Test
        return QuoteDaoApi() // Producción
    }

    // QuoteDatabaDao --> DatabaseModule.k

    // provideCharacterRepository  ¿PORQUE No tiene @Provides y no esta declarada aqui? Debido a que CharaterRepositoryImpl es y va a ser la unica implementación de la app (lo que pueden cambiar son sus daos, que son los que si se instancian aqui como 'provideCharacterDao'. Entonces esta implementación si que tiene '@Inject constructor', para que se implemente automaticamente los parametros que se pasan (los daos). Ahora bien se tiene que enlazar la interfaz de 'CharaterRepository' con 'provideCharacterRepository' por ello hay que hacer a través de un '@Bind' una conexión en una clase abstracta.

//   Ahora Hilt puede inyectarlos automáticamente al detectar '@Inject constructor()' en sus clases.
// 🛑 Alternativa (opcional): Si eliminas '@Provides' en AppModule, Hilt ya sabrá cómo inyectarlo automáticamente gracias a '@Inject constructor'.
// ✅ En este caso, Hilt sabrá que cuando pida 'CharaterRepository', debe crear 'CharaterRepositoryImpl' automáticamente.
// El problema es que Hilt no solo necesita saber cómo construir 'CharaterRepositoryImpl', sino también cómo enlazarlo con la interfaz 'CharaterRepository', para ello usamos el @Bind a través deuna clase abtracta. Por ello se crea DomainModule

//    @Provides
//    fun provideCharacterRepository(dao: CharacterDao, databaseDao: CharacterDatabaseDao): CharaterRepository {
//        return CharaterRepositoryImpl(dao, databaseDao)
//    }

    // Casos de uso de Character...
//    @Provides
//    fun provideGetAllCharactersUseCase(repository: CharaterRepository): GetAllCharactersUseCase {
//        return GetAllCharactersUseCaseImpl(repository)
//    }

    // ...
}

