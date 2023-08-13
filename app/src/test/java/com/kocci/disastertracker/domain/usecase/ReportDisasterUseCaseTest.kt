package com.kocci.disastertracker.domain.usecase

import com.kocci.disastertracker.data.enums.AvailableProvince
import com.kocci.disastertracker.domain.repository.ReportRepository
import com.kocci.disastertracker.domain.usecase.report.GetDisasterReportUseCase
import com.kocci.disastertracker.domain.usecase.report.GetDisasterReportUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ReportDisasterUseCaseTest {

    @Mock
    private lateinit var reportRepository: ReportRepository

    private lateinit var getReport: GetDisasterReportUseCase

    @Before
    fun setup() {
        getReport = GetDisasterReportUseCaseImpl(reportRepository)
    }

    @Test
    fun `when invoked, should return @params province code to repository`() = runTest {
        val dummy = AvailableProvince.JAKARTA
        val provinceName = dummy.name
        val provinceCode = dummy.codes
        `when`(reportRepository.getReportList(provinceCode, null)).thenReturn(flow<Nothing> {})

        getReport.invoke(provinceName, null).collect()
        Mockito.verify(reportRepository).getReportList(provinceCode, null)
    }

    @Test
    fun `when invoked, if @params provinceName is empty, should return null @params code to repository`() = runTest {
        `when`(reportRepository.getReportList(null, null)).thenReturn(flow<Nothing> {})

        getReport.invoke("", null).collect()
        Mockito.verify(reportRepository).getReportList(null, null)
    }
}