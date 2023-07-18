package com.bangkit.submission2.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.profDataStore by preferencesDataStore("settings")
class SettingPreferences constructor(context: Context) {

    private val settingDataStore = context.profDataStore
    private val themesKEY = booleanPreferencesKey("theme_settins")

    fun getThemeSettings(): Flow<Boolean> = settingDataStore.data.map {
            preferences -> preferences[themesKEY] ?: false
    }

    suspend fun saveThemeSettings(isDarkModeActive: Boolean){
        settingDataStore.edit { preferences ->
            preferences[themesKEY] = isDarkModeActive
        }
    }
}