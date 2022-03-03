package com.example.azureobserver.myapplication.domain.usecase

import com.example.azureobserver.myapplication.data.repository.ServiceBusRepository
import retrofit2.await
import javax.inject.Inject

interface SendMessageToServiceBusUseCase {
    suspend fun invoke(message: String) : Any
}

class SendMessageToServiceBusUseCaseImpl @Inject constructor(private val repository: ServiceBusRepository) : SendMessageToServiceBusUseCase {
    override suspend fun invoke(message: String): Any {
        val response = repository.sendMessageServiceBus(message).await()
        return response
    }
}



