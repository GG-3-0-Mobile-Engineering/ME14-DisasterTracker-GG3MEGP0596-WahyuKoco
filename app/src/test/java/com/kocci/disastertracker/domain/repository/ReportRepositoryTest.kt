package com.kocci.disastertracker.domain.repository

import com.kocci.disastertracker.data.repository.ReportRepositoryImpl
import com.kocci.disastertracker.data.source.local.preferences.SettingPreferences
import com.kocci.disastertracker.data.source.remote.response.ReportsApiResponse
import com.kocci.disastertracker.data.source.remote.service.ApiService
import com.kocci.disastertracker.util.mapper.ReportsMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
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

    @Before
    fun setup() = runTest {
        repo = ReportRepositoryImpl(apiService, preference, reportMapper)
    }

    @Test
    fun `getReportList, when invoked, should map api model to domain model`() = runTest {

        val dummyData = Mockito.mock(ReportsApiResponse::class.java)
        Mockito.`when`(apiService.getCrowdSourcingReport(null, null))
            .thenReturn(Response.success(dummyData))

        val flowPreference: Flow<Int> = flow {
            emit(SettingPreferences.DEFAULT_TIME_PERIOD)
        }
        Mockito.`when`(preference.reportPeriodPreference)
            .thenReturn(flowPreference)

        repo.getReportList(null, null).collect()
        Mockito.verify(apiService).getCrowdSourcingReport(null, null)
        Mockito.verify(reportMapper).convertReportApiResponseToDomain(ReportsApiResponse())
    }
}