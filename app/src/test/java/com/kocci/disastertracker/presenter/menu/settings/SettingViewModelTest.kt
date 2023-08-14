package com.kocci.disastertracker.presenter.menu.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kocci.disastertracker.MainDispatcherRule
import com.kocci.disastertracker.data.enums.AvailableReportPeriod
import com.kocci.disastertracker.data.source.local.preferences.SettingPreferences
import com.kocci.disastertracker.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class SettingViewModelTest {

    @get:Rule
    val rule = MainDispatcherRule()

    @get:Rule
    val rule2 = InstantTaskExecutorRule()

    @Mock
    private lateinit var preferences: SettingPreferences

    private lateinit var settingViewModel: SettingViewModel

    /**
     * Note :
     * I change live data (darkTheme & reportPeriod) from variable to function for a reason.
     * Variables are executed after class creation, making it difficult to test.
     * The problem is that we can't set the preference to return what we want.
     * Because it was all are set in the first time after class creation.
     * So, we use function to make it not executed immediately.
     */

    @Before
    fun setup() {
        settingViewModel = SettingViewModel(preferences)
    }

    @Test
    fun `liveData, should return data based on preferences`() = runTest {
        val expectedTimePeriod = AvailableReportPeriod.TODAY
        val expectedDarkTheme = false

        `when`(preferences.reportPeriodPreference).thenReturn(
            flow { emit(expectedTimePeriod.periodInSec) }
        )
        `when`(preferences.darkThemePreference).thenReturn(
            flow { emit(expectedDarkTheme) }
        )

        val actualTimePeriod = settingViewModel.timePeriodLiveData().getOrAwaitValue()
        val actualDarkTheme = settingViewModel.darkThemeLiveData().getOrAwaitValue()

        Assert.assertEquals(expectedTimePeriod, actualTimePeriod)
        Assert.assertEquals(expectedDarkTheme, actualDarkTheme)
    }

    @Test
    fun `when update user setting, should pass right @params to preference manager`() = runTest {
        val expectedReportPeriod = AvailableReportPeriod.ONE_WEEK
        val expectedDarkTheme = false
        settingViewModel.updateTimePeriod(expectedReportPeriod.showToUi)
        settingViewModel.updateDarkTheme(false)

        Mockito.verify(preferences).setReportPeriod(expectedReportPeriod.periodInSec)
        Mockito.verify(preferences).setDarkThemeState(expectedDarkTheme)
    }

    @Test
    fun `getAvailableTime, should have the same size with available time enum`() {
        val expected = AvailableReportPeriod.values().size
        val actual = settingViewModel.getAvailableTime().size

        Assert.assertEquals(expected, actual)
    }
}