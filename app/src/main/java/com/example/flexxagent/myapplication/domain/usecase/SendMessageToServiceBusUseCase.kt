package com.example.azureobserver.myapplication.domain.usecase

import com.example.azureobserver.myapplication.data.repository.ServiceBusRepository
import okhttp3.RequestBody
import retrofit2.await
import javax.inject.Inject

interface SendMessageToServiceBusUseCase {
    suspend fun invoke(message: RequestBody) : Any
}

class SendMessageToServiceBusUseCaseImpl @Inject constructor(private val repository: ServiceBusRepository) : SendMessageToServiceBusUseCase {
    override suspend fun invoke(message: RequestBody): Any {
        val response = repository.sendMessageServiceBus(message).await()
        return response
    }
}



