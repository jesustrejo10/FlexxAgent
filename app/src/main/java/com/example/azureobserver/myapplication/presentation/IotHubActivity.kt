package com.example.azureobserver.myapplication.presentation

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.azureobserver.databinding.ActivityIothubBinding
import com.example.azureobserver.myapplication.presentation.viewmodel.IotHubActivityViewModel
import com.microsoft.azure.sdk.iot.device.*
import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodCallback
import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodData
import com.microsoft.azure.sdk.iot.device.transport.IotHubConnectionStatus
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject
import org.liquidplayer.service.MicroService
import java.io.UnsupportedEncodingException

@AndroidEntryPoint
class IotHubActivity : AppCompatActivity() {
    lateinit var iotHubActivityViewModel: IotHubActivityViewModel
    lateinit var binding : ActivityIothubBinding
    private val connString = "HostName=retiaTest.azure-devices.net;DeviceId=MyAndroidDevice;SharedAccessKey=yhwhRxI8OdEH5eGjRizosLwCzYDZBhhG4M2Mrtj8FGo="
    lateinit var client : DeviceClient
    val protocol : IotHubClientProtocol = IotHubClientProtocol.MQTT
     var msgReceivedCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIothubBinding.inflate(layoutInflater)
        initButtons()
        setContentView(binding.root)
//        executeMicroservice()
    }

    private fun initButtons() {
        binding.initIotClient.setOnClickListener {
            initIotHubClient()
        }
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        iotHubActivityViewModel = ViewModelProvider(this).get(IotHubActivityViewModel::class.java)
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

     fun initIotHubClient() {
        client = DeviceClient(connString, protocol)
        try {

            client.registerConnectionStatusChangeCallback(IotHubConnectionStatusChangeCallbackLogger(),Object())
            client.open()
            val callback = MessageCallbacks()
            client.setMessageCallback(callback, null)
            client.subscribeToDeviceMethod(SampleDeviceMethodCallback(),applicationContext,DeviceMethodStatusCallBack(),null)
        } catch (e: java.lang.Exception) {
            System.err.println("Exception while opening IoTHub connection: $e")
            client.closeNow()
            println("Shutting down...")
        }
    }


    ///////TODO refactor this and take out of here the classes
    //This class is a callback, for execute a method from IotHub Server
    class SampleDeviceMethodCallback : DeviceMethodCallback {
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
        fun methodNotificationRunnable() {
//            val context =con
            val text: CharSequence =
                "Set Send Messages Interval to " + sendMessagesInterval + "ms"
//            val duration = Toast.LENGTH_LONG
//            val toast = Toast.makeText(context, text, duration)
//            toast.show()
            println("method nottification runneable ha sido ejecutado  y el texto que sige es la respuesta "+text)
        }


    }
    class MessageCallbacks : com.microsoft.azure.sdk.iot.device.MessageCallback {
        override fun execute(msg: Message, context: Any): IotHubMessageResult {
            println(
                "Received message with content: " + String(
                    msg.bytes,
                    Message.DEFAULT_IOTHUB_MESSAGE_CHARSET
                )
            )
//            msgReceivedCount++
//            val txtMsgsReceivedVal = findViewById<TextView>(R.id.txtMsgsReceivedVal)
//            txtMsgsReceivedVal.text = Integer.toString(msgReceivedCount)
//            txtLastMsgReceivedVal.setText(
//                "[" + String(
//                    msg.bytes,
//                    Message.DEFAULT_IOTHUB_MESSAGE_CHARSET
//                ) + "]"
//            )
            return IotHubMessageResult.COMPLETE
        }
    }

    protected class IotHubConnectionStatusChangeCallbackLogger :
        IotHubConnectionStatusChangeCallback {
        override fun execute(
            status: IotHubConnectionStatus,
            statusChangeReason: IotHubConnectionStatusChangeReason,
            throwable: Throwable?,
            callbackContext: Any
        ) {
            println()
            println("CONNECTION STATUS UPDATE: $status")
            println("CONNECTION STATUS REASON: $statusChangeReason")
            println("CONNECTION STATUS THROWABLE: " + if (throwable == null) "null" else throwable.message)
            println()
            if (throwable != null) {
                throwable.printStackTrace()
            }
            if (status == IotHubConnectionStatus.DISCONNECTED) {
                //connection was lost, and is not being re-established. Look at provided exception for
                // how to resolve this issue. Cannot send messages until this issue is resolved, and you manually
                // re-open the device client
            } else if (status == IotHubConnectionStatus.DISCONNECTED_RETRYING) {
                //connection was lost, but is being re-established. Can still send messages, but they won't
                // be sent until the connection is re-established
            } else if (status == IotHubConnectionStatus.CONNECTED) {
                //Connection was successfully re-established. Can send messages.
            }
        }
    }

    //This class is the callback for provide a status, of the method called in IotHub
    protected class DeviceMethodStatusCallBack : IotHubEventCallback {
        override fun execute(status: IotHubStatusCode, context: Any) {
            println("IoT Hub responded to device method operation with status " + status.name)
        }
    }




}
