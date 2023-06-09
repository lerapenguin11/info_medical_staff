package com.example.infomedicalstaff.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.business.model.DocModel
import com.example.infomedicalstaff.ui.fragments.HomeFragment
import com.example.infomedicalstaff.ui.fragments.chatList.ChatsListFragment
import com.example.infomedicalstaff.ui.fragments.groups.GroupsChatFragment
import com.example.infomedicalstaff.ui.fragments.single.SingleChatFragment
import com.example.infomedicalstaff.utilits.TYPE_CHAT
import com.example.infomedicalstaff.utilits.TYPE_GROUP
import com.example.infomedicalstaff.utilits.asTime
import com.example.infomedicalstaff.utilits.replaceFragment

class ChatsListAdapter() : RecyclerView.Adapter<ChatsListAdapter.ChatsViewHolder>() {

    val listItems = mutableListOf<CommonModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chats, parent, false)
        val holder = ChatsViewHolder(view)

        holder.itemView.setOnClickListener {
            when(listItems[holder.adapterPosition].type){
                TYPE_CHAT -> replaceFragment(SingleChatFragment(listItems[holder.adapterPosition]))
                TYPE_GROUP -> replaceFragment(GroupsChatFragment(listItems[holder.adapterPosition]))
            }
            replaceFragment(SingleChatFragment(listItems[holder.adapterPosition]))
        }

        return holder
    }

    override fun getItemCount(): Int = listItems.size
    override fun onBindViewHolder(holder: ChatsListAdapter.ChatsViewHolder, position: Int) {
        val chat: CommonModel = listItems[position]

        holder.nameGroup.text = chat.fullName
        holder.textGroup.text = chat.lastMessage
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