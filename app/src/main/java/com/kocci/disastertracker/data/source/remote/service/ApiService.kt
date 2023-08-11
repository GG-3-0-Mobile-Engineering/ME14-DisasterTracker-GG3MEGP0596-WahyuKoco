package com.kocci.disastertracker.data.source.remote.service

import com.kocci.disastertracker.data.source.local.preferences.SettingPreferences
import com.kocci.disastertracker.data.source.remote.response.ReportsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //hit endpoint here
    @GET("reports")
    suspend fun getCrowdSourcingReport(
        @Query("admin") provinceCode: String? = null,
        @Query("disaster") disasterType: String? = null,
        @Query("timeperiod") time: Int = SettingPreferences.DEFAULT_TIME_PERIOD
    ): Response<ReportsApiResponse>
}