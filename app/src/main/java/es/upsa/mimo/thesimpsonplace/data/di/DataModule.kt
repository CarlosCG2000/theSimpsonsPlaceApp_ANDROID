package es.upsa.mimo.thesimpsonplace.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.upsa.mimo.thesimpsonplace.data.daos.remote.CharacterDao
import es.upsa.mimo.thesimpsonplace.data.daos.remote.EpisodeDao
import es.upsa.mimo.thesimpsonplace.data.daos.remote.impl.CharacterDaoImpl
import es.upsa.mimo.thesimpsonplace.data.daos.remote.impl.EpisodeDaoImpl
import javax.inject.Singleton

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

// AppModule maneja repositorios, casos de uso y lógica de negocio.
@Module // Para proporcionar las dependencias
@InstallIn(SingletonComponent::class)
object DataModule {

//   Ahora Hilt puede inyectarlos automáticamente al detectar @Inject constructor() en sus clases.
    @Provides // Esto hace que Hilt nunca use @Inject constructor de CharacterDaoJson, porque ya le estás diciendo exactamente cómo crear la instancia. Aquí Hilt sabe que cuando alguien necesite CharaterDao, debe proporcionar una instancia de CharaterDaoImpl. Al tener la eleccion de json de test o produccion, lo implementamos manualmente pasandole el @Provides y luego la eplciaicon de dentro
    // ¿PORQUE @Provides? La implemención de dicho Dao no tiene' @Inject constructor' (no es automatizada), debido a que quiero proveerlo (con @Provides) de forma más manual al elegir yo si el dao de implementacioón sea para json de test o producción cambiando el parámetro recibido
    @Singleton // ✅ Solo debe haber una instancia de CharacterDao en toda la app
    fun provideCharacterDao(@ApplicationContext context: Context): CharacterDao {
        val test = false // Cambia a `true` o 'false' para usar d elos json de test a producción

        return if (test) {
            CharacterDaoImpl(context, "personajes_test.json", "imagenes_test.json")
        } else {
            CharacterDaoImpl(context, "personajes_data.json", "imagenes_data.json")
        }
    }

    // CharacterDatabaDao --> DatabaseModule.kt

    @Provides
    @Singleton
    fun provideEpisodeDao(@ApplicationContext context: Context): EpisodeDao {
        val test = false

        return if(test) {
            EpisodeDaoImpl(context, "episodios_test.json")
        }else {
            EpisodeDaoImpl(context, "episodios_data.json")
        }
    }

//  EpisodeDatabaDao --> DatabaseModule.k

    /**
    DataModule.kt (Fuentes de Datos y DataStore)

    Este módulo proporciona:
    ✔ CharacterDao y EpisodeDao, permitiendo elegir entre modo producción o test con archivos JSON.

    Ventaja: Permite cambiar entre archivos de producción y prueba sin modificar el código en otros lugares.
     */

// Por si quisiera cambiar de API seria aqui simplemente (si quiero pasarselo por parámetro debo de cambiar mi implementación de QuoteDaoApi()
//    ❌ Problema 2: 'provideQuoteDao' en AppModule es innecesario
//    🔍 Causa: Ya lo estás proveyendo en 'NetworkModule.kt'. Elimínalo de aqui AppModule.
//    @Provides
//    @Singleton
//    fun provideQuoteDao(): QuoteDao {
//        // XXXXX // Test
//        return QuoteDaoApi() // Producción
//    }

    // QuoteDatabaDao --> DatabaseModule.k

    // provideCharacterRepository
// ¿PORQUE No tiene @Provides y no esta declarada aqui? Debido a que CharaterRepositoryImpl es y va a ser la unica implementación de la app (lo que pueden cambiar son sus daos, que son los que si se instancian aqui como 'provideCharacterDao'. Entonces esta implementación si que tiene '@Inject constructor', para que se implemente automaticamente los parametros que se pasan (los daos). Ahora bien se tiene que enlazar la interfaz de 'CharaterRepository' con 'provideCharacterRepository' por ello hay que hacer a través de un '@Bind' una conexión en una clase abstracta.

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

