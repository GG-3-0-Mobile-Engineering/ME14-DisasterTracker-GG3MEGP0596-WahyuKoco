package com.kocci.disastertracker.domain.repository

import com.kocci.disastertracker.data.enums.AvailableReportPeriod
import com.kocci.disastertracker.data.repository.ReportRepositoryImpl
import com.kocci.disastertracker.data.source.local.preferences.PreferenceManager
import com.kocci.disastertracker.data.source.remote.response.ReportsApiResponse
import com.kocci.disastertracker.data.source.remote.service.ApiService
import com.kocci.disastertracker.util.mapper.ReportsMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ReportRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var preference: PreferenceManager

    @Mock
    private lateinit var reportMapper: ReportsMapper

    private lateinit var repo: ReportRepository

    @Before
    fun setup() = runTest {
        repo = ReportRepositoryImpl(apiService, preference, reportMapper)
        val dummyData = Mockito.mock(ReportsApiResponse::class.java)

        Mockito.`when`(apiService.getCrowdSourcingReport(null, null))
            .thenReturn(Response.success(dummyData))

        Mockito.`when`(preference.getReportPeriod())
            .thenReturn(AvailableReportPeriod.ONE_WEEK)
    }

    @Test
    fun `getReportList, if province args empty, should call api with null arguments`() = runTest {
        repo.getReportList("", null).collect()
        Mockito.verify(preference).getReportPeriod()
        Mockito.verify(apiService).getCrowdSourcingReport(
            provinceCode = null,
            null
        )
    }


    /**
     * Test emitted values here
     */

    @Test
    fun something() {

    }
}