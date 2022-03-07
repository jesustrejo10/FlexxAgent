package com.example.azureobserver.myapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.azureobserver.R
import com.example.azureobserver.myapplication.domain.model.request.AzureMessage

class MessageAdapter(val data: List<AzureMessage>) : RecyclerView.Adapter<MessageAdapter.MessageHolder>() {

    class MessageHolder(val view: View):RecyclerView.ViewHolder(view){
        fun render(messageFromServiceBus: AzureMessage){
            val messageView:TextView =view.findViewById(R.id.message)
//            messageView.text = messageFromServiceBus.message
        //            TODO Finish this recycler view, this recycler view should print the messages comming from service Bus
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MessageHolder(layoutInflater.inflate(R.layout.item_message,parent, false))
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val messageToShow = data.getOrNull(position)?: return
        holder.render(messageToShow)
    }
}