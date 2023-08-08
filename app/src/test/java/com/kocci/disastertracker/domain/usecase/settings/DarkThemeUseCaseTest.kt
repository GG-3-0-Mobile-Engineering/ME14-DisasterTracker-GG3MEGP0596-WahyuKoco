package com.kocci.disastertracker.domain.usecase.settings

import com.kocci.disastertracker.data.source.local.preferences.PreferenceManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DarkThemeUseCaseTest {


    @Mock
    private lateinit var preferenceManager: PreferenceManager

    private lateinit var useCase: DarkThemeUseCase

    @Before
    fun setup() {
        useCase = DarkThemeUseCaseImpl(preferenceManager)
    }

    @Test
    fun `use case should change dark theme preference to true or false when enabled or disabled`() {
        useCase.enableDarkTheme()
        Mockito.verify(preferenceManager).setDarkThemePreference(true)

        useCase.disableDarkTheme()
        Mockito.verify(preferenceManager).setDarkThemePreference(false)
    }

    @Test
    fun `use case should inform about current dark theme preferences`(){
        Mockito.`when`(preferenceManager.getDarkThemePreference()).thenReturn(true)

        val isDarkThemeEnabled = useCase.isDarkThemeEnabled()
        Assert.assertEquals(isDarkThemeEnabled, true)
    }


}