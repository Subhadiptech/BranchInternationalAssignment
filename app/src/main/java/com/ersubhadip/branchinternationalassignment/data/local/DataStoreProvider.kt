package com.ersubhadip.branchinternationalassignment.data.local

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile

fun provideDataStore(context: Context) = PreferenceDataStoreFactory.create(
    corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
    produceFile = { context.preferencesDataStoreFile(Session.STORAGE_NAME) })