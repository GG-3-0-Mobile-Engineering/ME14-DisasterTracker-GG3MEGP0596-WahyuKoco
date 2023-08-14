package com.kocci.disastertracker.util.helper

import com.kocci.disastertracker.data.enums.AvailableReportPeriod
import org.junit.Assert
import org.junit.Test

class ReportPeriodHelperTest {

    @Test
    fun `getAvailableTimePeriod, should have the same size with correlated enum`() {
        val actualSize = ReportPeriodHelper.getAvailableTimePeriod().size
        val expectedSize = AvailableReportPeriod.values().size

        Assert.assertEquals(actualSize, expectedSize)
    }

    @Test
    fun `convertToEnum, if enum not found, should throw IllegalArgumentException`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            ReportPeriodHelper.convertToEnum(123789)
        }

        Assert.assertThrows(IllegalArgumentException::class.java) {
            ReportPeriodHelper.convertToEnum("Last Today")
        }

    }

    @Test
    fun `convertToEnum, when invoked, should return the right enum`() {
        val expectedAnswer: AvailableReportPeriod = AvailableReportPeriod.ONE_WEEK
        val actualFromInt = ReportPeriodHelper.convertToEnum(expectedAnswer.periodInSec)
        val actualFromString = ReportPeriodHelper.convertToEnum(expectedAnswer.showToUi)

        Assert.assertEquals(actualFromInt, expectedAnswer)
        Assert.assertEquals(actualFromString, expectedAnswer)
    }
}