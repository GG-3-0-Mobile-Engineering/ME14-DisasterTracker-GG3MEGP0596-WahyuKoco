package com.kocci.disastertracker.di


import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {
    //this use case are used in application class, so we need it to be singleton.
}