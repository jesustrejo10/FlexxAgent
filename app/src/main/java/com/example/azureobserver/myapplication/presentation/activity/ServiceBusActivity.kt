package com.example.azureobserver.myapplication.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.azureobserver.databinding.ActivityServiceBusBinding
import com.example.azureobserver.myapplication.presentation.viewmodel.ServiceBusActivityViewModel
import com.google.gson.GsonBuilder
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
        initButtons()
    }

    private fun initButtons() {
        binding.getMessageServiceBus.setOnClickListener {
//            serviceBusViewModel.getMessageFromServiceBus()
        }

        binding.sendMessageServiceBus.setOnClickListener {
            sendMessageToServiceBus()
        }
    }

    private fun sendMessageToServiceBus() {
        if (isValidTheInput()) {
            serviceBusViewModel.sendMessageToServiceBus(binding.inputOfUser.text.toString())
        }
    }

    private fun isValidTheInput(): Boolean {
       if (binding.inputOfUser.text.toString().isEmpty()){
           showToast("The input is invalid")
           return false
       }
        return true
    }

    private fun createViewModel() {
       serviceBusViewModel = ViewModelProvider(this).get(ServiceBusActivityViewModel::class.java)
    }

    private fun showToast(message : String  ) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()
    }
}