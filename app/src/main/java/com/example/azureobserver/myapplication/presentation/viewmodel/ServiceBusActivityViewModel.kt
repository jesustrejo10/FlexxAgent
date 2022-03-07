package com.example.azureobserver.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private var _messageFromServiceBus = MutableLiveData<AzureMessage>()
    val messageFromServiceBus : LiveData<AzureMessage>
        get() = _messageFromServiceBus

    private var _messagesForRecyclerView = MutableLiveData<List<AzureMessage>>()
    val messagesForRecyclerView: LiveData<List<AzureMessage>>
        get() = _messagesForRecyclerView

    init {
        initRecyclerViewData()
    }

    private fun initRecyclerViewData() {
        _messagesForRecyclerView.value = mutableListOf()
    }
    fun addDataToRecyclerView(){
        _messagesForRecyclerView.value?.plus(messageFromServiceBus.value)
    }

    fun getMessageFromServiceBus(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = getMessageUseCase.invoke()
//            if (response.message.isNotEmpty())
//            {
//                _messageFromServiceBus.postValue(response)
//            }
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
        val requestBodyText = """<?xml version="1.0" encoding="utf-8"?>
                    <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                      <soap12:Body>
                        <Login xmlns="http://tempuri.org/">
                          <messageFromApplication> ${message}</messageFromApplication>
                          <password>123456</password>
                        </Login>
                      </soap12:Body>
                    </soap12:Envelope>"""
        val requestBody: RequestBody =
            RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        return requestBody
    }


}



