package com.example.azureobserver.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azureobserver.myapplication.domain.model.entities.BearerToken
import com.example.azureobserver.myapplication.domain.usecase.GetTokenForServiceBusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getTokenForServiceBusUseCase:GetTokenForServiceBusUseCase): ViewModel() {
    private var _isTheTokenInApp = MutableLiveData<Boolean>()
    val isTheTokenInApp : LiveData<Boolean>
        get ()= _isTheTokenInApp
    init{
        _isTheTokenInApp.postValue(false)
    }
     fun getAzureServiceBusToken() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getTokenForServiceBusUseCase.invoke()
            if (response != null) {
                BearerToken.token = response.tokenType+" " + response.token
                _isTheTokenInApp.postValue(true)
            }
        }
    }

}