package com.example.azureobserver.myapplication.domain.model.entities

import com.google.gson.annotations.SerializedName

data class AzureServiceBusToken(
    @SerializedName("token_type" ) val tokenType :String,
    @SerializedName("expires_on") val expireTime: String,
    @SerializedName("resource") val kindOfResource:String,
    @SerializedName("access_token") val token:String

)
