package com.example.azureobserver.myapplication.data

import com.example.azureobserver.myapplication.domain.model.entities.AzureServiceBusToken
import retrofit2.Call
import retrofit2.http.*

interface MicrosoftLoginEndPoints {

    @FormUrlEncoded
    @POST("2b77b30c-6af3-47ce-8cca-b70cef3e7654/oauth2/token")
    fun getToken(

                 @Field("grant_type") grantType : String,
                 @Field("client_id") clientId : String,
                 @Field("client_secret") clientSecret : String,
                 @Field("resource") resourceUrl: String) : Call<AzureServiceBusToken>
}