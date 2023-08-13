package com.kocci.disastertracker.domain.usecase.report

import com.kocci.disastertracker.domain.model.Reports
import com.kocci.disastertracker.domain.reactive.Async
import com.kocci.disastertracker.domain.repository.ReportRepository
import com.kocci.disastertracker.util.helper.ProvinceHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDisasterReportUseCaseImpl @Inject constructor(
    private val repository: ReportRepository,
) : GetDisasterReportUseCase {
    override fun invoke(provinceName: String?, disasterType: String?): Flow<Async<List<Reports>>> {
        return flow {
            val trimmedProvinceName = provinceName?.trim()
            val provinceCode = if (!trimmedProvinceName.isNullOrEmpty()) {
                try {
                    ProvinceHelper.getProvinceCode(trimmedProvinceName)
                } catch (e: Exception) {
                    emit(Async.Error("Error getting province code: ${e.message}"))
                    return@flow
                }
            } else {
                null
            }
            val reportList = repository.getReportList(provinceCode, disasterType)
            emitAll(reportList)
        }
    }
}
