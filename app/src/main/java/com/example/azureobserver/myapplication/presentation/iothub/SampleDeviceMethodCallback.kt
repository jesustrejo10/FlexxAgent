package com.example.azureobserver.myapplication.presentation.iothub


import android.widget.TextView
import com.example.azureobserver.myapplication.presentation.ConnectionRemoteMethodIotHubCall
import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodCallback
import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException


//This class is a callback, for execute a method from IotHub Server
class SampleDeviceMethodCallback(val text: TextView) : DeviceMethodCallback {
    var sendMessagesInterval: Int = 5000
    override fun call(methodName: String, methodData: Any, context: Any): DeviceMethodData {
        val deviceMethodData: DeviceMethodData
        deviceMethodData = try {
            when (methodName) {
                "setSendMessagesInterval" -> {
                    val status: Int = method_setSendMessagesInterval(methodData)
                    DeviceMethodData(status, "executed $methodName")
                }
                else -> {
                    val status: Int = method_default(methodData)
                    DeviceMethodData(status, "executed $methodName")
                }
            }
        } catch (e: java.lang.Exception) {
            val status: Int = ConnectionRemoteMethodIotHubCall.METHOD_THROWS
            DeviceMethodData(status, "Method Throws $methodName")
        }
        return deviceMethodData
    }

    fun method_default(data: Any): Int {
        println("invoking default method for this device")
        // Insert device specific code here
        return ConnectionRemoteMethodIotHubCall.METHOD_NOT_DEFINED
    }

    @Throws(UnsupportedEncodingException::class, JSONException::class)
    private fun method_setSendMessagesInterval(methodData: Any): Int {
        val payload = String((methodData as ByteArray)).replace("\"", "")
        val obj = JSONObject(payload)
        sendMessagesInterval = obj.getInt("sendInterval")
        methodNotificationRunnable()
        return ConnectionRemoteMethodIotHubCall.METHOD_SUCCESS
    }

    private fun methodNotificationRunnable() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                text.text = sendMessagesInterval.toString()
            } catch (t: Throwable) {
            }
        }
    }
}