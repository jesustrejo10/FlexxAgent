package com.example.azureobserver.myapplication.domain.usecase


import com.example.azureobserver.myapplication.data.repository.ServiceBusRepository
import com.example.azureobserver.myapplication.domain.model.entities.ServiceBusMessageResponse
import retrofit2.await
import javax.inject.Inject


interface GetMessageFromServiceBusUseCase {
    suspend fun invoke() : ServiceBusMessageResponse
}

class GetMessageFromServiceBusUseCaseImpl @Inject constructor(private val repository : ServiceBusRepository) : GetMessageFromServiceBusUseCase{
    override suspend fun invoke(): ServiceBusMessageResponse {
        val response = repository.getServiceBusMessages().await()
        return response
    }
}