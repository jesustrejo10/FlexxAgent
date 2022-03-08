package com.example.azureobserver.myapplication.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.azureobserver.databinding.ActivityMainBinding
import com.example.azureobserver.myapplication.domain.model.entities.BearerToken
import com.example.azureobserver.myapplication.presentation.IotHubActivity
import com.example.azureobserver.myapplication.presentation.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createViewModel()
        initButtons()
        initObservers()
    }

    private fun initObservers() {
        mainActivityViewModel.isTheTokenInApp.observe(this , Observer { isTheTokenInTheApp : Boolean ->
            if (isTheTokenInTheApp)
                navigateToServiceBusActivity()
        })
    }

    private fun createViewModel() {
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    private fun initButtons() {
        binding.iotHubServiceStart.setOnClickListener {
            navigateToIotHubActivity()
        }
        binding.serviceBusStart.setOnClickListener {
            mainActivityViewModel.getAzureServiceBusToken()
        }
    }

    private fun navigateToIotHubActivity() {
        val intent = Intent(this, IotHubActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToServiceBusActivity() {
        val intent = Intent(this, ServiceBusActivity::class.java)
        startActivity(intent)
    }



    private fun showToast(message : String  ) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()
    }
}