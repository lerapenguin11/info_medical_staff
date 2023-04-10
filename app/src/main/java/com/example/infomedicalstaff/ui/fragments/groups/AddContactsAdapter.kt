package com.example.infomedicalstaff.ui.fragments.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.ui.fragments.HomeFragment
import com.example.infomedicalstaff.ui.fragments.chatList.ChatsListFragment
import com.example.infomedicalstaff.ui.fragments.single.SingleChatFragment
import com.example.infomedicalstaff.utilits.asTime
import com.example.infomedicalstaff.utilits.replaceFragment

class AddContactsAdapter : RecyclerView.Adapter<AddContactsAdapter.AddContactsViewHolder>() {

    val listItems = mutableListOf<CommonModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddContactsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_user, parent, false)
        val holder = AddContactsViewHolder(view)

        holder.itemView.setOnClickListener {
            //replaceFragment(SingleChatFragment(listItems[holder.adapterPosition]))

            if (listItems[holder.adapterPosition].choice){
                holder.addChoice.visibility = View.INVISIBLE
                listItems[holder.adapterPosition].choice = false
                AddContactsFragment.listContacts.remove(listItems[holder.adapterPosition])
            } else {
                holder.addChoice.visibility = View.VISIBLE
                listItems[holder.adapterPosition].choice = true
                AddContactsFragment.listContacts.add(listItems[holder.adapterPosition])
            }
        }

        return holder
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: AddContactsAdapter.AddContactsViewHolder, position: Int) {
        holder.addNameGroup.text = listItems[position].userName
        holder.addStateGroup.text = listItems[position].state
        //holder.iconGroup.setBackgroundResource(R.drawable.user)
        //holder.time.text = listItems[position].timeStamp.toString().asTime()

    }

    fun updateListItem(item : CommonModel){
        listItems.add(item)
        notifyItemInserted(listItems.size)
    }

    class AddContactsViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val addNameGroup : TextView = view.findViewById(R.id.tv_add_name_user)
        val addStateGroup : TextView = view.findViewById(R.id.tv_add_user_appearance)
        //val iconGroup : CircleImageView = view.findViewById(R.id.iv_add_user)
        val addChoice : ConstraintLayout = view.findViewById(R.id.cl_add_choice)
    }
}