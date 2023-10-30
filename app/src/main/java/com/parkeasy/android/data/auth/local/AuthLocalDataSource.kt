package com.parkeasy.android.data.auth.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * AuthLocalDataSource class.
 */
class AuthLocalDataSource @Inject constructor(private val settingsDataStore: DataStore<Preferences>) {
    /**
     * Saves the user ID to the data store.
     *
     * @param userId The user ID to be saved.
     */
    suspend fun saveUserId(userId: String) {
        settingsDataStore.edit {
            it[stringPreferencesKey("user_id")] = userId
        }
    }

    /**
     * Deletes the user ID from the settings data store.
     *
     * This method is used to remove the user ID from the settings data store, effectively
     * logging out the user.
     */
    suspend fun deleteUserId() {
        settingsDataStore.edit {
            it[stringPreferencesKey("user_id")] = ""
        }
    }

    /**
     * Retrieves the current user ID from the settings data store.
     *
     * @return A Flow emitting the current user ID as a String. If the user ID is not found, an empty String is emitted.
     */
    fun getCurrentUserId() =
        settingsDataStore.data.map { it[stringPreferencesKey("user_id")] ?: "" }
}