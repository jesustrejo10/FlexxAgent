package com.example.azureobserver.myapplication.di

import com.example.azureobserver.myapplication.data.repository.ServiceBusRepository
import com.example.azureobserver.myapplication.domain.usecase.GetMessageFromServiceBusUseCase
import com.example.azureobserver.myapplication.domain.usecase.GetMessageFromServiceBusUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetMessageFromServicceBusUseCase(repository: ServiceBusRepository) : GetMessageFromServiceBusUseCase{
        return GetMessageFromServiceBusUseCaseImpl(repository)
    }
}