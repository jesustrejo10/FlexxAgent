package com.example.azureobserver.myapplication.presentation


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.azureobserver.databinding.ActivityIothubBinding
import com.example.azureobserver.myapplication.presentation.iothub.DeviceMethodStatusCallBack
import com.example.azureobserver.myapplication.presentation.iothub.IotHubConnectionStatusChangeCallbackLogger
import com.example.azureobserver.myapplication.presentation.iothub.MessageCallbacks
import com.example.azureobserver.myapplication.presentation.iothub.SampleDeviceMethodCallback
import com.example.azureobserver.myapplication.presentation.viewmodel.IotHubActivityViewModel
import com.microsoft.azure.sdk.iot.device.*
import dagger.hilt.android.AndroidEntryPoint
import org.liquidplayer.service.MicroService


@AndroidEntryPoint
class IotHubActivity : AppCompatActivity() {
    lateinit var iotHubActivityViewModel: IotHubActivityViewModel
    lateinit var binding : ActivityIothubBinding
    private val connString = "HostName=retiaTest.azure-devices.net;DeviceId=MyAndroidDevice;SharedAccessKey=yhwhRxI8OdEH5eGjRizosLwCzYDZBhhG4M2Mrtj8FGo="
    lateinit var client : DeviceClient
    val protocol : IotHubClientProtocol = IotHubClientProtocol.MQTT


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIothubBinding.inflate(layoutInflater)
        createViewModel()
        initButtons()
        setContentView(binding.root)

    }

    private fun createViewModel() {
        iotHubActivityViewModel = ViewModelProvider(this).get(IotHubActivityViewModel::class.java)
    }

    private fun initButtons() {
        binding.initIotClient.setOnClickListener {
            initIotHubClient()
        }
    }

     private fun initIotHubClient() {
        client = DeviceClient(connString, protocol)
        try {
            client.registerConnectionStatusChangeCallback(IotHubConnectionStatusChangeCallbackLogger(),Object())
            client.open()
            val callback = MessageCallbacks()
            client.setMessageCallback(callback, null)
            client.subscribeToDeviceMethod(SampleDeviceMethodCallback(binding.iotHubReceived),applicationContext,
                DeviceMethodStatusCallBack(),null)
        } catch (e: java.lang.Exception) {
            System.err.println("Exception while opening IoTHub connection: $e")
            client.closeNow()
            println("Shutting down...")
        }
    }
}
