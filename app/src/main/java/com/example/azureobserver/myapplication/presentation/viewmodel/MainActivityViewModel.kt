package com.example.azureobserver.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azureobserver.myapplication.domain.usecase.GetTokenForServiceBusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getTokenForServiceBusUseCase:GetTokenForServiceBusUseCase): ViewModel() {
    init{
        println("the view model has been created okay")
        viewModelScope.launch(Dispatchers.IO) {
            var response = getTokenForServiceBusUseCase.invoke()
            if (response != null)
            {
                println()
            }
        }
    }

}