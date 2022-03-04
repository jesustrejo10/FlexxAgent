package com.example.azureobserver.myapplication.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.azureobserver.databinding.ActivityServiceBusBinding
import com.example.azureobserver.myapplication.presentation.viewmodel.ServiceBusActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceBusActivity : AppCompatActivity() {
    lateinit var binding : ActivityServiceBusBinding
    lateinit var serviceBusViewModel : ServiceBusActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createViewModel()
    }

    private fun createViewModel() {
       serviceBusViewModel = ViewModelProvider(this).get(ServiceBusActivityViewModel::class.java)
    }
}