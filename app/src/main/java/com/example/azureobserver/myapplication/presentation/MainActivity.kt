package com.example.azureobserver.myapplication.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.azureobserver.databinding.ActivityMainBinding
import com.example.azureobserver.myapplication.presentation.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.liquidplayer.service.MicroService

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        executeMicroservice()


    }


    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        return super.onCreateView(parent, name, context, attrs)
    }

    private fun executeMicroservice() {

        //Callback  for the ready event
        val readyListener = MicroService.EventListener {
                service, _, _ -> service.emit("ping")
        }

        //Callback for pong event
        val pongListener = MicroService.EventListener {
                _, _, jsonObject ->
            val message = jsonObject.getString("message")
            runOnUiThread {  }
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
