package com.example.infomedicalstaff.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.DifferCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.utilits.CURRENT_UID
import com.example.infomedicalstaff.utilits.DiffUtilCallback
import com.example.infomedicalstaff.utilits.asTime
import java.text.SimpleDateFormat
import java.util.*

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatViewHolder>() {

    private var mListMessageCache = emptyList<CommonModel>()
    private lateinit var mDiffResult : DiffResult

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)

        return SingleChatViewHolder(view)
    }

    override fun getItemCount(): Int = mListMessageCache.size

    override fun onBindViewHolder(holder: SingleChatViewHolder, position: Int) {
        if(mListMessageCache[position].fromText == CURRENT_UID){
            holder.blockUserMessage.visibility = View.VISIBLE
            holder.blockReceivingMessage.visibility = View.GONE
            holder.messageUser.text = mListMessageCache[position].text
            holder.messageUserTime.text
            holder.messageUserTime.text =
                mListMessageCache[position].timeStamp.toString().asTime()
        }else {
            holder.blockUserMessage.visibility = View.GONE
            holder.blockReceivingMessage.visibility = View.VISIBLE
            holder.messageReceiving.text = mListMessageCache[position].text
            holder.messageReceivingTime.text
            holder.messageReceivingTime.text =
                mListMessageCache[position].timeStamp.toString().asTime()
        }
    }

    fun addItem(item:CommonModel){
        val newList = mutableListOf<CommonModel>()
        newList.addAll(mListMessageCache)


        if(!newList.contains(item)) newList.add(item)

        newList.sortBy { it.timeStamp.toString() }
        mDiffResult = DiffUtil.calculateDiff(DiffUtilCallback(mListMessageCache,newList))
        mDiffResult.dispatchUpdatesTo(this)
        mListMessageCache = newList
    }

    @SuppressLint("NonConstantResourceId")
    class SingleChatViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val blockUserMessage : ConstraintLayout = view.findViewById(R.id.block_message_user)
        val messageUser : TextView = view.findViewById(R.id.tv_message_user)
        val messageUserTime : TextView = view.findViewById(R.id.tv_message_user_time)

        val blockReceivingMessage : ConstraintLayout = view.findViewById(R.id.block_message_receiving)
        val messageReceiving : TextView = view.findViewById(R.id.tv_message_receiving)
        val messageReceivingTime : TextView = view.findViewById(R.id.tv_message_receiving_time)
    }
}


