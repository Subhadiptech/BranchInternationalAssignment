package com.ersubhadip.branchinternationalassignment.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class Session @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        const val STORAGE_NAME = "user_details"
        private const val TOKEN = "token"
        val token = stringPreferencesKey(TOKEN)
    }

    suspend fun getAuthToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[token]
    }


    suspend fun setUserAuth(authToken: String) {
        dataStore.edit { preference ->
            preference[token] = authToken
        }
    }
}