package com.kocci.disastertracker.di

import com.kocci.disastertracker.domain.usecase.report.GetDisasterReportUseCase
import com.kocci.disastertracker.domain.usecase.report.GetDisasterReportUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun provideGetDisasterReport(impl: GetDisasterReportUseCaseImpl): GetDisasterReportUseCase


}