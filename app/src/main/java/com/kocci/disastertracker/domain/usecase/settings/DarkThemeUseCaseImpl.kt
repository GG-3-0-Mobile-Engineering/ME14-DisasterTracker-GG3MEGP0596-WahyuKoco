package com.kocci.disastertracker.domain.usecase.settings

import com.kocci.disastertracker.data.source.local.preferences.PreferenceManager
import com.kocci.disastertracker.util.helper.ThemeHelper
import javax.inject.Inject

class DarkThemeUseCaseImpl @Inject constructor(
    private val preference: PreferenceManager,
) : DarkThemeUseCase {


    override fun shouldEnableDarkTheme() {
        val isDarkThemeEnabled = preference.getDarkThemePreference()
        if (isDarkThemeEnabled) {
            ThemeHelper.enableDarkTheme()
        } else {
            ThemeHelper.disableDarkTheme()
        }
    }

    override fun enableDarkTheme() {
        preference.setDarkThemePreference(true)
        ThemeHelper.enableDarkTheme()
    }

    override fun disableDarkTheme() {
        preference.setDarkThemePreference(false)
        ThemeHelper.disableDarkTheme()
    }

    override fun isDarkThemeEnabled(): Boolean {
        return preference.getDarkThemePreference()
    }
}