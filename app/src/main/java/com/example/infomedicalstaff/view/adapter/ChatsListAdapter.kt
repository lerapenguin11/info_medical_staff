package com.example.infomedicalstaff.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R

class ChatsListAdapter : RecyclerView.Adapter<ChatsListAdapter.ChatsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chats, parent, false)

        return ChatsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holderHourly: ChatsListAdapter.ChatsViewHolder, position: Int) {

    }

    inner class ChatsViewHolder(view : View) : RecyclerView.ViewHolder(view){

    }
}