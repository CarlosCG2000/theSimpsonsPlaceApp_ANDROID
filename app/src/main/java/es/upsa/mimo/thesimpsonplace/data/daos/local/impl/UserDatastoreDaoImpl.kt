package es.upsa.mimo.thesimpsonplace.data.daos.local.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import es.upsa.mimo.thesimpsonplace.data.entities.user.Language
import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import es.upsa.mimo.thesimpsonplace.data.daos.local.UserDatastoreDao
import es.upsa.mimo.thesimpsonplace.data.di.AppModule.UserDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class UserDatastoreDaoImpl @Inject constructor( @UserDataStore private val dataStore: DataStore<Preferences> ): UserDatastoreDao {

    private object PreferencesKeys {
        val USERNAME = stringPreferencesKey("username")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val LANGUAGE = stringPreferencesKey("language")
    }

    override val userPreferencesFlow: Flow< UserPreference > = dataStore.data.map { preferences ->
        val username = preferences[PreferencesKeys.USERNAME] ?: "Desconocido"
        val darkMode = preferences[PreferencesKeys.DARK_MODE] == true
        val languageCode = preferences[PreferencesKeys.LANGUAGE] ?: Language.SPANISH.code
        val language = Language.fromCode(languageCode)

        UserPreference(username, darkMode, language)
    }

    override suspend fun updateUser(userPreference: UserPreference) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USERNAME] = userPreference.username
            preferences[PreferencesKeys.DARK_MODE] = userPreference.darkMode
            preferences[PreferencesKeys.LANGUAGE] = userPreference.language.code
        }
    }

}