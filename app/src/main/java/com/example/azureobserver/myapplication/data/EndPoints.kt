package com.example.azureobserver.myapplication.data

import com.example.azureobserver.myapplication.domain.model.request.AzureMessage
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST


    interface EndPoints {

        @POST("myqueuetest2/messages/head")
        fun requestMessages(@Header("Authorization") token :String) : Call<AzureMessage>
    }
