package com.onusant.apitask.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class PreferenceRepository(private val datastore: DataStore<Preferences>) {

    fun <T>getPreference(key: Preferences.Key<T>): Flow<T?> {
        return datastore.data
            .map { it[key] }
            .catch {
                if (it is IOException) emit(null)
                else throw it
            }
    }

    suspend fun <T>setPreference(key: Preferences.Key<T>, value: T) {
        datastore.edit { it[key] = value }
    }

}