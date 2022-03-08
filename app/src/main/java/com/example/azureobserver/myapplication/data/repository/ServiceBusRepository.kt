package com.example.azureobserver.myapplication.data.repository


import com.example.azureobserver.myapplication.data.EndPoints
import com.example.azureobserver.myapplication.domain.model.entities.BearerToken
import com.example.azureobserver.myapplication.domain.model.entities.ServiceBusMessageResponse
import okhttp3.RequestBody
import retrofit2.Call
import javax.inject.Inject

interface ServiceBusRepository {
    fun getServiceBusMessages(): Call<ServiceBusMessageResponse>
    fun  sendMessageServiceBus(message: RequestBody): Call<Any>
}


class ServiceBusRepositoryImpl @Inject constructor(private val endPoints: EndPoints) :ServiceBusRepository {
    override fun getServiceBusMessages(): Call<ServiceBusMessageResponse> {
        return endPoints.requestMessages(token = BearerToken.token)
    }

    override fun sendMessageServiceBus(message: RequestBody): Call<Any> {
        return endPoints.sendMessage(
            token = BearerToken.token,
            message = message)
    }

}