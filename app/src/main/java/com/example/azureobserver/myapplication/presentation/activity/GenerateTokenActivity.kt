package com.example.azureobserver.myapplication.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.azureobserver.databinding.ActivityGenerateTokenBinding
import com.example.azureobserver.myapplication.presentation.viewmodel.GenerateTokenActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerateTokenActivity : AppCompatActivity() {
    lateinit var binding : ActivityGenerateTokenBinding
    lateinit var generateTokenActivityViewModel: GenerateTokenActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerateTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createViewModel()
        initButtons()
    }

    private fun createViewModel() {
        generateTokenActivityViewModel = ViewModelProvider(this).get(GenerateTokenActivityViewModel::class.java)
    }

    private fun initButtons() {
        binding.iotHubServiceStart.setOnClickListener {
            showToast(message = "the iot service button works")
        }
        binding.serviceBusStart.setOnClickListener {
            showToast(message = "serviceBusIsWorking")
        }
    }

    private fun showToast(message : String  ) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()
    }
}