package com.example.azureobserver.myapplication.data

import com.example.azureobserver.myapplication.domain.model.request.AzureMessage
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.entity.StringEntity
import org.xmlpull.v1.XmlSerializer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.io.InputStream
import java.io.StringWriter


interface EndPoints {

        @POST("myqueuetest2/messages/head")
        fun requestMessages(@Header("Authorization") token :String) : Call<AzureMessage>

        @POST("myqueuetest2/messages")
        fun sendMessage(@Header("Authorization") token:String, @Body message: String) : Call<Any>
    }
