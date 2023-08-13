package com.kocci.disastertracker.data.enums

import com.kocci.disastertracker.util.helper.ReportPeriodHelper

enum class AvailableReportPeriod(val periodInSec: Int, val showToUi: String) {
    ONE_WEEK(604800, ReportPeriodHelper.Show.ONE_WEEK),
    FIVE_DAYS(432000, ReportPeriodHelper.Show.FIVE_DAYS),
    THREE_DAYS(259200, ReportPeriodHelper.Show.THREE_DAYS),
    TWO_DAYS(172800, ReportPeriodHelper.Show.TWO_DAYS),
    TODAY(86400, ReportPeriodHelper.Show.TODAY)
}