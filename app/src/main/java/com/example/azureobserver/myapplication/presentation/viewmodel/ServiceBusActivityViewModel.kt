package com.example.azureobserver.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azureobserver.myapplication.domain.usecase.GetMessageFromServiceBusUseCase
import com.example.azureobserver.myapplication.domain.usecase.SendMessageToServiceBusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ServiceBusActivityViewModel @Inject constructor(
    private val getMessageUseCase: GetMessageFromServiceBusUseCase
    , private val sendMessageToServiceBusUseCase: SendMessageToServiceBusUseCase
): ViewModel() {
    init {

    }

    fun getMessageFromServiceBus(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = getMessageUseCase.invoke()
            if (response.message.isNotEmpty())
            {
                println(response.message)
            }
        }
    }

    fun sendMessageToServiceBus(message : String ){

//        val message = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<note>\n" +
//                "  <to>Tove</to>\n" +
//                "  <from>Jani</from>\n" +
//                "  <heading>Reminder</heading>\n" +
//                "  <body>Don't forget me this weekend!</body>\n" +
//                "</note>"
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = sendMessageToServiceBusUseCase.invoke(message = message)
            }catch (e : Exception){
                println("There was a problem "+ e.message)
            }
        }
    }
}