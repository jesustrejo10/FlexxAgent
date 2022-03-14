package com.example.azureobserver.myapplication.presentation.iothub

import com.microsoft.azure.sdk.iot.device.IotHubMessageResult
import com.microsoft.azure.sdk.iot.device.Message

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