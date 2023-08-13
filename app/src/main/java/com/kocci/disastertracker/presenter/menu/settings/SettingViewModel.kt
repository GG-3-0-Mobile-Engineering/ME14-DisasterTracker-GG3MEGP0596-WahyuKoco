package com.kocci.disastertracker.presenter.menu.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kocci.disastertracker.data.source.local.preferences.SettingPreferences
import com.kocci.disastertracker.util.helper.MyLogger
import com.kocci.disastertracker.util.helper.ReportPeriodHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingPreferences: SettingPreferences
) : ViewModel() {

    init {
        MyLogger.e("VIEW MODEL CREATED")
    }

    val darkThemePreference = settingPreferences.darkThemePreference.asLiveData()
    val timePeriodPreference =
        settingPreferences.reportPeriodPreference.map {
            ReportPeriodHelper.convertToEnum(it)
        }.asLiveData()

    fun updateTimePeriod(timePeriod: String) = viewModelScope.launch {
        val time = ReportPeriodHelper.convertToEnum(timePeriod).periodInSec
        settingPreferences.setReportPeriod(time)
    }

    fun updateDarkTheme(state: Boolean) = viewModelScope.launch {
        settingPreferences.setDarkThemeState(state)
    }

    fun getAvailableTime() = ReportPeriodHelper.getAvailableTimePeriod()
}

