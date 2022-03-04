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
class IotHubActivityViewModel @Inject constructor(
    private val getMessageUseCase: GetMessageFromServiceBusUseCase, private val sendMessageToServiceBusUseCase: SendMessageToServiceBusUseCase)
    : ViewModel() {
    init {

    }
    }