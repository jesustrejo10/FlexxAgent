package com.example.azureobserver.myapplication.data.repository


import com.example.azureobserver.myapplication.data.EndPoints
import com.example.azureobserver.myapplication.domain.model.entities.AzureServiceBusToken
import com.example.azureobserver.myapplication.domain.model.entities.BearerToken
import com.example.azureobserver.myapplication.domain.model.request.AzureMessage
import com.google.gson.Gson
import retrofit2.Call
import javax.inject.Inject

interface ServiceBusRepository {
    fun getServiceBusMessages(): Call<AzureMessage>
    fun sendMessageServiceBus(message: String): Call<Any>
}


class ServiceBusRepositoryImpl @Inject constructor(private val endPoints: EndPoints) :ServiceBusRepository {
    override fun getServiceBusMessages(): Call<AzureMessage> {
        return endPoints.requestMessages(token = BearerToken.token)
    }

    override fun sendMessageServiceBus(message: String): Call<Any> {
        val gson : Gson = Gson()
        val requestServiceBusMessage = AzureMessage(message)
        val messageRequest = gson.toJson(requestServiceBusMessage)
        return endPoints.sendMessage(
            token = BearerToken.token,
            message = messageRequest)
    }



}