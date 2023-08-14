package com.kocci.disastertracker.presenter.report

import com.kocci.disastertracker.MainDispatcherRule
import com.kocci.disastertracker.domain.usecase.report.GetDisasterReportUseCase
import com.kocci.disastertracker.util.helper.NotificationHelper
import com.kocci.disastertracker.util.helper.ProvinceHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ReportViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getDisasterReportUseCase: GetDisasterReportUseCase

    @Mock
    private lateinit var notificationHelper: NotificationHelper
    private lateinit var reportViewModel: ReportViewModel

    @Before
    fun setup() {
        reportViewModel = ReportViewModel(getDisasterReportUseCase, notificationHelper)
    }

    @Test
    fun `getAvailableProvince, when invoked, should have the same size with the helper`() {
        val actualSize = reportViewModel.getAvailableProvince().size
        val expectedSize = ProvinceHelper.getAvailableProvince().size

        Mockito.verify(getDisasterReportUseCase).invoke()

        Assert.assertEquals(actualSize, expectedSize)
    }

}