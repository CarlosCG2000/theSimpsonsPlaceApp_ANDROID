package es.upsa.mimo.thesimpsonplace.data.sources.database.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import es.upsa.mimo.thesimpsonplace.data.sources.database.GameDatastoreDao
import es.upsa.mimo.thesimpsonplace.di.AppModule.GameDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameDatastoreDaoImpl @Inject constructor(@GameDataStore private val dataStore: DataStore<Preferences>):
    GameDatastoreDao {

    private object PreferencesKeys {
        val ACIERTOS = intPreferencesKey("aciertos")
        val PREGUNTAS = intPreferencesKey("preguntas")
    }

    override val gameStatsFlow: Flow<Pair<Int, Int>> = dataStore.data.map { preferences ->
        val aciertos = preferences[PreferencesKeys.ACIERTOS] ?: 0
        val preguntas = preferences[PreferencesKeys.PREGUNTAS] ?: 0
        Pair(aciertos, preguntas) // es el Pair<Int, Int> que retorna
    }

    override suspend fun updateStats(aciertos: Int, preguntas: Int) {

        val currentStats = gameStatsFlow.first() // Obtiene el valor actual sin usar collect

        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ACIERTOS] = aciertos + currentStats.first
            preferences[PreferencesKeys.PREGUNTAS] = preguntas + currentStats.second
        }
    }

    override suspend fun resetStats() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.ACIERTOS)
            preferences.remove(PreferencesKeys.PREGUNTAS)
        }
    }
}