package com.kocci.disastertracker.domain.usecase.settings

interface DarkThemeUseCase { //UpdateThemeUseCase
    fun shouldEnableDarkTheme() //actually redundant, use func below

    /**
     * Update :
     * Update theme (state :Boolean)
     * note : better use invoke() (atau 1 public function) untuk use case
     */

    fun enableDarkTheme() // incorporate
    fun disableDarkTheme() // incorporate
    fun isDarkThemeEnabled() : Boolean
}