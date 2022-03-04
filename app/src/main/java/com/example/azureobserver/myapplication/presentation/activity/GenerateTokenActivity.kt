package com.example.azureobserver.myapplication.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.azureobserver.databinding.ActivityGenerateTokenBinding

class GenerateTokenActivity : AppCompatActivity() {
    lateinit var binding : ActivityGenerateTokenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerateTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
    }

    private fun initButtons() {
        binding.iotHubServiceStart.setOnClickListener {
            showToast(message = "the iot service button works")
        }
    }

    private fun showToast(message : String  ) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()
    }
}