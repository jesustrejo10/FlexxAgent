package com.example.azureobserver.myapplication.domain.usecase

import com.example.azureobserver.myapplication.data.repository.LoginAzureRepository
import com.example.azureobserver.myapplication.data.repository.ServiceBusRepository
import com.example.azureobserver.myapplication.domain.model.entities.AzureServiceBusToken
import com.example.azureobserver.myapplication.domain.model.request.AzureMessage
import retrofit2.await
import javax.inject.Inject


interface GetTokenForServiceBusUseCase {
    suspend fun invoke() : AzureServiceBusToken?
}

class GetTokenForServiceBusImpl @Inject constructor(private val repository : LoginAzureRepository) : GetTokenForServiceBusUseCase{
    override suspend fun invoke(): AzureServiceBusToken? {
        val response = repository.getServiceBusToken()?.await()
        return response

    }
}

