package com.example.azureobserver.myapplication.domain.usecase


import com.example.azureobserver.myapplication.data.repository.ServiceBusRepository
import com.example.azureobserver.myapplication.domain.model.request.AzureMessage
import retrofit2.await
import javax.inject.Inject


interface GetMessageFromServiceBusUseCase {
    suspend fun invoke() : AzureMessage
}

class GetMessageFromServiceBusUseCaseImpl @Inject constructor(private val repository : ServiceBusRepository) : GetMessageFromServiceBusUseCase{
    override suspend fun invoke(): AzureMessage {
        val response = repository.getServiceBusMessages().await()
        return response
    }
}