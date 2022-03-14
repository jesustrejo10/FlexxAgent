package com.example.azureobserver.myapplication.presentation.iothub

import com.microsoft.azure.sdk.iot.device.IotHubEventCallback
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode

//This class is the callback for provide a status, of the method called in IotHub
 class DeviceMethodStatusCallBack : IotHubEventCallback {
    override fun execute(status: IotHubStatusCode, context: Any) {
        println("IoT Hub responded to device method operation with status " + status.name)
    }
}
