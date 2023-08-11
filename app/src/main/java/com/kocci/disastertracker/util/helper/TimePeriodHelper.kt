package com.kocci.disastertracker.util.helper

import com.kocci.disastertracker.data.enums.AvailableReportPeriod


object TimePeriodHelper {
    object Show {
        // object that we will be presented to user
        const val ONE_WEEK = "Last Week"
        const val FIVE_DAYS = "Last Five Days"
        const val THREE_DAYS = "Last Three Days"
        const val TWO_DAYS = "Last Two Days"
        const val TODAY = "Today"
    }

    fun getAvailableTimePeriod(): List<AvailableReportPeriod> {
        return AvailableReportPeriod.values().toList()
    }

    fun convertToEnum(timePeriod: Int): AvailableReportPeriod {
        AvailableReportPeriod.values().forEach { enum ->
            if (timePeriod == enum.periodInSec)
                return enum
        }
        throw IllegalArgumentException("No time period found - from int")
    }

    fun convertToEnum(timePeriod: String): AvailableReportPeriod {
        AvailableReportPeriod.values().forEach { enum ->
            if (timePeriod == enum.showToUi)
                return enum
        }
        throw IllegalArgumentException("No time period found - from string")
    }

}