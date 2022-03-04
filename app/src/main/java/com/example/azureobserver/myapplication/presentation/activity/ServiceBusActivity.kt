package com.example.azureobserver.myapplication.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.azureobserver.databinding.ActivityServiceBusBinding

class ServiceBusActivity : AppCompatActivity() {
    lateinit var binding : ActivityServiceBusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBusBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}