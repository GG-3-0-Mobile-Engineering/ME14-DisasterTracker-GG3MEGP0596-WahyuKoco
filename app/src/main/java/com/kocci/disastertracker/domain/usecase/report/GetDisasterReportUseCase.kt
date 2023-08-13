package com.kocci.disastertracker.domain.usecase.report

import com.kocci.disastertracker.domain.model.Reports
import com.kocci.disastertracker.domain.reactive.Async
import kotlinx.coroutines.flow.Flow

interface GetDisasterReportUseCase {
    operator fun invoke(
        provinceName: String? = null,
        disasterType: String? = null,
    ): Flow<Async<List<Reports>>>
}