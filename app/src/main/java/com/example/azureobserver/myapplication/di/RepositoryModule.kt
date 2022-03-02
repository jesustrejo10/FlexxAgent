package com.example.azureobserver.myapplication.di

import com.example.azureobserver.myapplication.data.EndPoints
import com.example.azureobserver.myapplication.data.repository.ServiceBusRepository
import com.example.azureobserver.myapplication.data.repository.ServiceBusRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideServiceBusRepository(endPoints: EndPoints) : ServiceBusRepository{
        return ServiceBusRepositoryImpl(endPoints)
    }
}