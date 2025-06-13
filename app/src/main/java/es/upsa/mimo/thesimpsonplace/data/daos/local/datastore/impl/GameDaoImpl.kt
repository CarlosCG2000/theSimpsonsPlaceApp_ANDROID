package es.upsa.mimo.thesimpsonplace.data.daos.local.datastore.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import es.upsa.mimo.thesimpsonplace.data.daos.local.datastore.GameDao
import es.upsa.mimo.thesimpsonplace.data.di.DatabaseModule.GameDataStore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameDaoImpl @Inject constructor( @GameDataStore private val dataStore: DataStore<Preferences> ): GameDao {

    private object PreferencesKeys {
        val ACIERTOS = intPreferencesKey("aciertos")
        val PREGUNTAS = intPreferencesKey("preguntas")
    }

    override val gameStatsFlow: Flow<Pair<Int, Int>> = dataStore.data.map { preferences ->
        val aciertos = preferences[PreferencesKeys.ACIERTOS] ?: 0
        val preguntas = preferences[PreferencesKeys.PREGUNTAS] ?: 0
        Pair(aciertos, preguntas) // Retorna Pair<Int, Int> -> flowOf(aciertos to preguntas)
    }

    override suspend fun updateStats(aciertos: Int, preguntas: Int) {
        val currentStats = gameStatsFlow.first() // Suspende la ejecución hasta que el primer valor esté disponible del flujo

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