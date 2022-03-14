package com.example.flexxagent.myapplication.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.azureobserver.databinding.ActivityJavaScriptBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.liquidplayer.service.MicroService

class JavaScriptActivity : AppCompatActivity() {
    lateinit var binding : ActivityJavaScriptBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityJavaScriptBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
    }

    private fun initButtons() {
        binding.CallJsFunction.setOnClickListener {
            executeJavaScriptMicroservice()
        }
    }


    private fun executeJavaScriptMicroservice() {

        //Callback  for the ready event
        val readyListener = MicroService.EventListener {
                service, _, _ -> service.emit("ping")
        }

        //Callback for pong event
        val pongListener = MicroService.EventListener {
                _, _, jsonObject ->
            val message = jsonObject.getString("message")
                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        binding.textFromJs.text = message
                    } catch (t: Throwable) {
                    }
                }
        }

        //These listener are from the Js file, when these events happen,
        // A callback will be executed, readyListener and pongListener respectively
        val startListener =
            MicroService.ServiceStartListener{
                    service ->
                service.addEventListener("ready", readyListener)
                service.addEventListener("pong", pongListener)
            }
        //Uri for the microservice
        val uri = MicroService.Bundle(this, "example")
        val service = MicroService(this, uri,
            startListener)
        service.start()
    }
}