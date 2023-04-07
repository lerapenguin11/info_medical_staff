package com.example.infomedicalstaff.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.utilits.asTime

class ChatsListAdapter : RecyclerView.Adapter<ChatsListAdapter.ChatsViewHolder>() {

    val listItems = mutableListOf<CommonModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chats, parent, false)

        return ChatsViewHolder(view)
    }

    override fun getItemCount(): Int = listItems.size
    override fun onBindViewHolder(holder: ChatsListAdapter.ChatsViewHolder, position: Int) {
        holder.nameGroup.text = listItems[position].userName
        holder.textGroup.text = listItems[position].lastMessage
        //holder.iconGroup.setBackgroundResource(R.drawable.user)
        //holder.time.text = listItems[position].timeStamp.toString().asTime()

    }

    fun updateListItem(item : CommonModel){
        listItems.add(item)
        notifyItemInserted(listItems.size)
    }

    class ChatsViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val nameGroup : TextView = view.findViewById(R.id.tv_name_user)
        val textGroup : TextView = view.findViewById(R.id.tv_user_appearance)
        //val iconGroup : TextView = view.findViewById(R.id.iv_user)
        val time : TextView = view.findViewById(R.id.tv_time)
    }
}