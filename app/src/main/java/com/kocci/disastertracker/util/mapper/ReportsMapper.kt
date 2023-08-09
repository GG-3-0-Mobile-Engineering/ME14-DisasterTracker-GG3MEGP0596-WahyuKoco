package com.kocci.disastertracker.util.mapper

import com.kocci.disastertracker.data.source.remote.response.EarthquakeReportData
import com.kocci.disastertracker.data.source.remote.response.FireReportData
import com.kocci.disastertracker.data.source.remote.response.FloodReportData
import com.kocci.disastertracker.data.source.remote.response.HazeReportData
import com.kocci.disastertracker.data.source.remote.response.ReportsApiResponse
import com.kocci.disastertracker.data.source.remote.response.VolcanoReportData
import com.kocci.disastertracker.data.source.remote.response.WindReportData
import com.kocci.disastertracker.domain.model.Coordinates
import com.kocci.disastertracker.domain.model.reports.EarthquakeReports
import com.kocci.disastertracker.domain.model.reports.FireReports
import com.kocci.disastertracker.domain.model.reports.FloodReport
import com.kocci.disastertracker.domain.model.reports.GeneralReports
import com.kocci.disastertracker.domain.model.reports.HazeReports
import com.kocci.disastertracker.domain.model.Reports
import com.kocci.disastertracker.domain.model.reports.VolcanoReports
import com.kocci.disastertracker.domain.model.reports.WindReports
import com.kocci.disastertracker.util.helper.DateHelper
import javax.inject.Inject


class ReportsMapper @Inject constructor(){

    fun convertReportApiResponseToDomain(reportsApiResponse: ReportsApiResponse): List<Reports> {
        val reportList = mutableListOf<Reports>()
        reportsApiResponse.result?.objects?.output?.geometries?.let { item ->
            item.map {
                val coordinates = Coordinates(
                    lat = (it?.coordinates?.get(1) ?: 0.0), lng = it?.coordinates?.get(0) ?: 0.0
                )
                val title = it?.properties?.title ?: "No title"
                var body = it?.properties?.text ?: "No Description"
                val imgUrl = it?.properties?.imageUrl
                val date = it?.properties?.createdAt ?: ""
                val beautifyDate = DateHelper.beautifyDate(date)
                val disasterType = it?.properties?.disasterType ?: "Not Specified"

                if (body.isEmpty()) {
                    body = "No Description"
                }

                it?.properties?.reportData?.let { reportData ->
                    when (reportData) {
                        is EarthquakeReportData -> {
                            val reports = EarthquakeReports(
                                body,
                                coordinates,
                                imgUrl,
                                title,
                                disasterType,
                                beautifyDate,
                                reportData.reportType
                            )
                            reportList.add(reports)
                        }

                        is FloodReportData -> {
                            val reports = FloodReport(
                                body,
                                coordinates,
                                imgUrl,
                                title,
                                disasterType,
                                beautifyDate,
                                reportData.reportType,
                                reportData.floodDepth ?: 0
                            )
                            reportList.add(reports)
                        }

                        is FireReportData -> {
                            val reports = FireReports(
                                body,
                                coordinates,
                                imgUrl,
                                title,
                                disasterType,
                                beautifyDate,
                                reportData.reportType,
                                reportData.fireDistance ?: 0.0
                            )
                            reportList.add(reports)
                        }

                        is HazeReportData -> {
                            val reports = HazeReports(
                                body,
                                coordinates,
                                imgUrl,
                                title,
                                disasterType,
                                beautifyDate,
                                reportData.reportType
                            )
                            reportList.add(reports)
                        }

                        is VolcanoReportData -> {
                            val reports = VolcanoReports(
                                body,
                                coordinates,
                                imgUrl,
                                title,
                                disasterType,
                                beautifyDate,
                                reportData.reportType
                            )
                            reportList.add(reports)
                        }

                        is WindReportData -> {
                            val reports = WindReports(
                                body,
                                coordinates,
                                imgUrl,
                                title,
                                disasterType,
                                beautifyDate,
                                reportData.reportType
                            )
                            reportList.add(reports)
                        }
                    }
                } ?: run {
                    val reports = GeneralReports(
                        body,
                        coordinates,
                        imgUrl,
                        title,
                        disasterType,
                        beautifyDate,
                    )
                    reportList.add(reports)
                }


//            val reports = Reports(coordinates, title, body, beautifyDate, imgUrl, disasterType)
//            reportList.add(reports)
            }
        }
        return reportList
    }
}
