package com.kocci.disastertracker

import android.app.Application
import com.kocci.disastertracker.data.source.local.preferences.SettingPreferences
import com.kocci.disastertracker.util.helper.NotificationHelper
import com.kocci.disastertracker.util.helper.ThemeHelper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class RootApplication : Application() {
    /**
     * This application class called once the application built.
     * Register activity lifecycle, or something that should be setup at first app opens.
     * Override the onCreate if you wish
     */

    @Inject
    lateinit var setting: SettingPreferences

    override fun onCreate() {
        super.onCreate()
        observeDarkTheme()
        NotificationHelper.createNotificationChannel(this)
    }

    private fun observeDarkTheme() {
        CoroutineScope(Dispatchers.Main).launch {
            setting.darkThemePreference.collect { isDarkTheme ->
                if (isDarkTheme) {
                    ThemeHelper.enableDarkTheme()
                } else {
                    ThemeHelper.disableDarkTheme()
                }
            }
        }
//        setting.darkThemePreference.asLiveData().observeForever { isDarkTheme ->
//            if (isDarkTheme) {
//                ThemeHelper.enableDarkTheme()
//            } else {
//                ThemeHelper.disableDarkTheme()
//            }
//        }
    }
}