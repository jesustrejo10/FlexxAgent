package com.example.azureobserver.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azureobserver.myapplication.domain.model.entities.ServiceBusMessageResponse
import com.example.azureobserver.myapplication.domain.model.request.AzureMessage
import com.example.azureobserver.myapplication.domain.usecase.GetMessageFromServiceBusUseCase
import com.example.azureobserver.myapplication.domain.usecase.SendMessageToServiceBusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import javax.inject.Inject
import javax.xml.transform.stream.StreamResult


@HiltViewModel
class ServiceBusActivityViewModel @Inject constructor(
    private val getMessageUseCase: GetMessageFromServiceBusUseCase
    , private val sendMessageToServiceBusUseCase: SendMessageToServiceBusUseCase
): ViewModel() {

    private var _messageFromServiceBus = MutableLiveData<ServiceBusMessageResponse>()
    val messageFromServiceBus : LiveData<ServiceBusMessageResponse>
        get() = _messageFromServiceBus

    init {
    }

    fun getMessageFromServiceBus(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = getMessageUseCase.invoke()
            if (response.messageFromApplication!!.isNotEmpty())
            {
                println("la respuesta es ${response}")
                _messageFromServiceBus.postValue(response)
            }
        }
    }

    fun sendMessageToServiceBus(message: String, ){
        val requestBody: RequestBody = madeRequestForSendMessage(message)
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = sendMessageToServiceBusUseCase.invoke(requestBody)
            }catch (e : Exception){
                println("There was a problem "+ e.message)
            }
        }
    }

    private fun madeRequestForSendMessage(message: String): RequestBody {
        val requestBodyText =
            """
                <Login version="2.0">
                    <messageFromApplication> {$message}</messageFromApplication>
                    <password> Best luck the next time</password>
                </Login>"""
        val requestBody: RequestBody =
            RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        return requestBody
    }


}



