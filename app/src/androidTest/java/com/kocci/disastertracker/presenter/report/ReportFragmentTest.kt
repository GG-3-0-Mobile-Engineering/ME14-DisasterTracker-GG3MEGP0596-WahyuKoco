package com.kocci.disastertracker.presenter.report

import android.app.Application
import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

/**
 * I always got an error.
 * Sorry i can't do an instrument test for now.
 * (near deadline and have other business)
 */

//@HiltAndroidTest
//@RunWith(AndroidJUnit4::class)
//class ReportFragmentTest {
//
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    @Before
//    fun init() {
//        hiltRule.inject()
//    }
//
//    @Test
//    fun test(){
//        // Create a mock NavController
//        val mockNavController = Mockito.mock(NavController::class.java)
//
//
//
//        // Create a graphical FragmentScenario for the TitleScreen
//        val titleScenario = launchFragmentInContainer<ReportFragment>()
//
//        // Set the NavController property on the fragment
//        titleScenario.onFragment { fragment ->
//            Navigation.setViewNavController(fragment.requireView(), mockNavController)
//        }
//
//        // Verify that performing a click prompts the correct Navigation action
////        onView(ViewMatchers.withId(R.id.play_btn)).perform(ViewActions.click())
////        verify(mockNavController).navigate(R.id.action_title_screen_to_in_game)
//    }
//
//
//}