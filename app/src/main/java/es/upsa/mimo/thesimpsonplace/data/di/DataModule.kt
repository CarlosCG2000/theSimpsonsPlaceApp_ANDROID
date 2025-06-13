package es.upsa.mimo.thesimpsonplace.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.upsa.mimo.thesimpsonplace.BuildConfig
import es.upsa.mimo.thesimpsonplace.data.daos.remote.CharacterDao
import es.upsa.mimo.thesimpsonplace.data.daos.remote.EpisodeDao
import es.upsa.mimo.thesimpsonplace.data.daos.remote.impl.CharacterDaoImpl
import es.upsa.mimo.thesimpsonplace.data.daos.remote.impl.EpisodeDaoImpl
import es.upsa.mimo.thesimpsonplace.utils.Logger
import javax.inject.Singleton

/**
 * ¿Por qué te funciona igual con o sin @Inject constructor?
 * El motivo por el que tu código funciona igual con o sin @Inject constructor es porque estás usando @Provides en el DataModule. Esto significa que Hilt no necesita inyectar automáticamente las dependencias en tus clases, ya que tú mismo las estás proporcionando manualmente en el módulo.
 *
 * Diferencia clave entre @Inject constructor y @Provides
 * Si usas @Provides en el módulo, no es necesario @Inject constructor en las clases porque ya estás diciéndole manualmente a Hilt cómo crear cada instancia.
 * Si usas @Inject constructor en las clases, entonces no necesitas @Provides en el módulo (excepto si necesitas lógica de inicialización personalizada, como en CharacterDaoJson).
 *
 * ¿Cuándo elegir una opción u otra?
 * 	• Dejar @Provides en DataModule si en el futuro necesitas cambiar la implementación fácilmente.
 * 	• Eliminar @Provides y solo usar @Inject constructor si la implementación no va a cambiar nunca y prefieres menos código en DataModule.
 */

/**
    DataModule.kt (Fuentes de Datos y DataStore)
    Este módulo proporciona:
    ✔ CharacterDao y EpisodeDao, permitiendo elegir entre modo producción o test con archivos JSON.
        Ventaja: Permite cambiar entre archivos de producción y prueba sin modificar el código en otros lugares.
 */
// DataModule maneja repositorios, casos de uso y lógica de negocio.
@Module // Para proporcionar las dependencias
@InstallIn(SingletonComponent::class)
object DataModule: Logger {

    // Ahora Hilt puede inyectarlos automáticamente al detectar '@Inject constructor()' en sus clases.
    @Provides // Esto hace que Hilt nunca use '@Inject constructor' de 'CharacterDaoJson', porque ya le estás diciendo exactamente cómo crear la instancia. Aquí Hilt sabe que cuando alguien necesite 'CharacterDao', debe proporcionar una instancia de 'CharaterDaoImpl'. Al tener la eleccion de json de test o producción, lo implementamos manualmente pasandole el '@Provides' y luego la eleccion de dentro
    // ¿PORQUE '@Provides'? La implemención de dicho Dao no tiene '@Inject constructor' (no es automatizada), debido a que quiero proveerlo (con @Provides) de forma más manual al elegir yo si el Dao de implementación sea para json de test o producción cambiando el parámetro recibido
    @Singleton // Solo debe haber una instancia de 'CharacterDao' en toda la app
    fun provideCharacterDao(@ApplicationContext context: Context): CharacterDao {
        logInfo( "Proveyendo CharacterDao en BuildConfig.JSON_TEST: ${BuildConfig.JSON_TEST}")

        // Si el buildConfig es 'true' entonces se usa el de test, con un json más reducido
        return  if (BuildConfig.JSON_TEST)
                    CharacterDaoImpl(context, "personajes_test.json", "imagenes_test.json")
                else
                    CharacterDaoImpl(context, "personajes_data.json", "imagenes_data.json")
    }

    @Provides
    @Singleton
    fun provideEpisodeDao(@ApplicationContext context: Context): EpisodeDao {
        logInfo( "Proveyendo EpisodeDao en BuildConfig.JSON_TEST: ${BuildConfig.JSON_TEST}")

        // Si el buildConfig es 'true' entonces se usa el de test, con un json más reducido
        return  if (BuildConfig.JSON_TEST)
                    EpisodeDaoImpl(context, "episodios_test.json")
                else
                    EpisodeDaoImpl(context, "episodios_data.json")
    }
}

