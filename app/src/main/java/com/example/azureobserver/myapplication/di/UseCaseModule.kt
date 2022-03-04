package com.example.azureobserver.myapplication.di

import com.example.azureobserver.myapplication.data.repository.ServiceBusRepository
import com.example.azureobserver.myapplication.domain.usecase.*
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

    @Provides
    fun provideSendMessageToServiceBusUseCase(repository: ServiceBusRepository) : SendMessageToServiceBusUseCase{
        return SendMessageToServiceBusUseCaseImpl(repository)
    }
    @Provides
    fun provideGetTokenForServiceBusUseCase(repository: ServiceBusRepository) : GetTokenForServiceBusUseCase{
        return GetTokenForServiceBusImpl(repository)
    }
}