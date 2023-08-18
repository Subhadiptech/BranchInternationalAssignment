package com.ersubhadip.branchinternationalassignment.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class Session(private val dataStore: DataStore<Preferences>) {
    companion object {
        const val STORAGE_NAME = "user_details"
        private const val TOKEN = "token"
        val token = stringPreferencesKey(TOKEN)
    }

    fun getAuthToken(): Flow<String?> {
        return (dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preference ->
            preference[token]
        })
    }


    suspend fun setUserAuth(authToken: String) {
        dataStore.edit { preference ->
            preference[token] = authToken
        }
    }
}