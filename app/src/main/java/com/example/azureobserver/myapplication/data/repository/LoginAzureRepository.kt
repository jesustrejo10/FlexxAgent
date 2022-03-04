package com.example.azureobserver.myapplication.data.repository

import com.example.azureobserver.myapplication.data.MicrosoftLoginEndPoints
import com.example.azureobserver.myapplication.domain.model.entities.AzureServiceBusToken
import com.example.azureobserver.myapplication.domain.model.request.AuthenticationTokenRequest
import retrofit2.Call
import javax.inject.Inject


interface LoginAzureRepository {
    fun getServiceBusToken(): Call<AzureServiceBusToken>?
}


class LoginAzureRepositoryImp @Inject constructor(private val microsoftLoginEndPoints : MicrosoftLoginEndPoints): LoginAzureRepository  {
    override fun getServiceBusToken(): Call<AzureServiceBusToken>? {
        val requestForGetToken = AuthenticationTokenRequest()
         var response =   microsoftLoginEndPoints.getToken(
             grantType = requestForGetToken.grantType,
             clientId = requestForGetToken.clientId,
             clientSecret = requestForGetToken.clientSecret,
             resourceUrl = requestForGetToken.resource)
    return response
    }
}