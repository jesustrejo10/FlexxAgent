package com.example.azureobserver.myapplication.data

import com.example.azureobserver.myapplication.domain.model.entities.Feed
import com.example.azureobserver.myapplication.domain.model.entities.ServiceBusMessageResponse
import com.example.azureobserver.myapplication.domain.model.request.AzureMessage
import okhttp3.RequestBody

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface EndPoints {

        @POST("myqueuetest2/messages/head")
        fun requestMessages(@Header("Authorization") token :String) : Call<Feed>

        @POST("myqueuetest2/messages")
        fun sendMessage(@Header("Authorization") token:String, @Body message: RequestBody) : Call<Any>

    }
