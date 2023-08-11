package com.kocci.disastertracker.presenter.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kocci.disastertracker.domain.model.Reports
import com.kocci.disastertracker.domain.reactive.Async
import com.kocci.disastertracker.domain.usecase.ReportDisasterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val useCase: ReportDisasterUseCase,
) : ViewModel() {

    val availableProvince = useCase.getAvailableProvince()

    /**
     * We use this because i need to listen reports in 2 different ways.
     * (onViewCreated and onMapsReady)
     */
    private var _reports: MutableLiveData<Async<List<Reports>>> = MutableLiveData()
    val reports: LiveData<Async<List<Reports>>> get() = _reports


    init {
        callApi(
            provinceName = null,
            disasterType = null
        )
    }

    fun callApi(provinceName: String?, disasterType: String? = null) {
        viewModelScope.launch {
            useCase.getAllReportData(provinceName, disasterType).collect {
                _reports.value = it
            }
        }
    }

    fun showNotificationForFlood(depth: Int) {
        useCase.showFloodDangerNotification(depth)
    }

}