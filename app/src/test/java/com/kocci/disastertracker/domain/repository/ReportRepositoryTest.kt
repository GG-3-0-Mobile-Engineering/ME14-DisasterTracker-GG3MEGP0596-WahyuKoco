package com.kocci.disastertracker.domain.repository

import com.kocci.disastertracker.data.repository.ReportRepositoryImpl
import com.kocci.disastertracker.data.source.local.preferences.SettingPreferences
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
    private lateinit var preference: SettingPreferences

    @Mock
    private lateinit var reportMapper: ReportsMapper

    private lateinit var repo: ReportRepository

    /**
     * Test is currently error because i changed the preferences from static to reactive.
     * Root problem is in : when i mock the flow preference.
     * Holy cow. Maybe i have a bad design?
     */

    @Before
    fun setup() = runTest {
        repo = ReportRepositoryImpl(apiService, preference, reportMapper)
        val dummyData = Mockito.mock(ReportsApiResponse::class.java)
        Mockito.`when`(apiService.getCrowdSourcingReport(null, null))
            .thenReturn(Response.success(dummyData))

//        Mockito.`when`(preference.reportPeriodPreference.first())
//            .thenReturn(SettingPreferences.DEFAULT_TIME_PERIOD) //here is the problem that makes all my test broken
    }

    @Test
    fun `repository should map data to domain logic`() = runTest {
        repo.getReportList(null, null).collect()
        Mockito.verify(apiService).getCrowdSourcingReport(null, null)
        Mockito.verify(reportMapper).convertReportApiResponseToDomain(ReportsApiResponse())
    }

    @Test
    fun `if the province is empty string, call api with null province argument instead`() =
        runTest {
            repo.getReportList("", null).collect()
            Mockito.verify(apiService).getCrowdSourcingReport(null, null)
        }

    /**
     * Test emitted values here
     */

}