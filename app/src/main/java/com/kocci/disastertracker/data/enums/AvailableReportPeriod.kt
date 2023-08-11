package com.kocci.disastertracker.data.enums

import com.kocci.disastertracker.util.helper.TimePeriodHelper

enum class AvailableReportPeriod(val periodInSec: Int, val showToUi: String) {
    ONE_WEEK(604800, TimePeriodHelper.Show.ONE_WEEK),
    FIVE_DAYS(432000, TimePeriodHelper.Show.FIVE_DAYS),
    THREE_DAYS(259200, TimePeriodHelper.Show.THREE_DAYS),
    TWO_DAYS(172800, TimePeriodHelper.Show.TWO_DAYS),
    TODAY(86400, TimePeriodHelper.Show.TODAY)
}