package com.example.azureobserver.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azureobserver.myapplication.domain.usecase.GetMessageFromServiceBusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getMessageUseCase: GetMessageFromServiceBusUseCase)
    : ViewModel() {
    init {
        getMessageFromServiceBus()
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
}