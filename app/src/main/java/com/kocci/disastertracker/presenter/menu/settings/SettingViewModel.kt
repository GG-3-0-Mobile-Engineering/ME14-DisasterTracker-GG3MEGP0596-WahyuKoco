package com.kocci.disastertracker.presenter.menu.settings

import androidx.lifecycle.ViewModel
import com.kocci.disastertracker.domain.usecase.settings.DarkThemeUseCase
import com.kocci.disastertracker.domain.usecase.settings.ReportTimePeriodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val useCase: DarkThemeUseCase,
    private val timePeriodUseCase: ReportTimePeriodUseCase,
) : ViewModel() {

    val userTimePreference = timePeriodUseCase.showTimePeriod()

    fun enableDarkTheme() = useCase.enableDarkTheme()
    fun disableDarkTheme() = useCase.disableDarkTheme()
    val isDarkModeEnabled = useCase.isDarkThemeEnabled()

    fun showAvailableTime() = timePeriodUseCase.showAvailableTimePeriod()
    fun setTimePeriod(period: String) {
        timePeriodUseCase.setTimePeriod(period)
    }
}