package com.kocci.disastertracker.data.source.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingPreferences @Inject constructor(
    private val datastore: DataStore<Preferences>
) {
    private object Keys {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        val REPORT_TIME_PERIOD = intPreferencesKey("report_time_period")
    }

    val darkThemePreference: Flow<Boolean> = datastore.data.map { pref ->
        pref[Keys.IS_DARK_THEME] ?: false
    }

    val reportPeriodPreference: Flow<Int> = datastore.data.map { pref ->
        pref[Keys.REPORT_TIME_PERIOD] ?: DEFAULT_TIME_PERIOD
    }

    suspend fun setDarkThemeState(state: Boolean) {
        datastore.edit {
            it[Keys.IS_DARK_THEME] = state
        }
    }

    suspend fun setReportPeriod(time: Int) {
        datastore.edit {
            it[Keys.REPORT_TIME_PERIOD] = time
        }
    }

    companion object {
        const val DEFAULT_TIME_PERIOD: Int = 604800
    }
}