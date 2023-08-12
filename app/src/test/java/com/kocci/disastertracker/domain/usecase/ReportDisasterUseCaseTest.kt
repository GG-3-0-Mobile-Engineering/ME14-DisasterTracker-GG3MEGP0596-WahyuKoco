package com.kocci.disastertracker.domain.usecase

import com.kocci.disastertracker.domain.repository.ReportRepository
import com.kocci.disastertracker.util.helper.NotificationHelper
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReportDisasterUseCaseTest {

    @Mock
    private lateinit var reportRepository: ReportRepository

    @Mock
    private lateinit var notificationHelper: NotificationHelper

    private lateinit var reportDisasterUseCase: ReportDisasterUseCase

    @Before
    fun setup() {
        reportDisasterUseCase = ReportDisasterUseCaseImpl(reportRepository, notificationHelper)
    }


    @Test
    fun `get data, when passing province argument to repository, should be trimmed first`() {
        val expectedProvinceResult = "Jakarta Selatan"
        Mockito.`when`(reportRepository.getReportList(expectedProvinceResult, null))
            .thenReturn(flow<Nothing> { })

        reportDisasterUseCase.getAllReportData("  Jakarta Selatan        ", null)
        Mockito.verify(reportRepository).getReportList(expectedProvinceResult, null)
    }
}