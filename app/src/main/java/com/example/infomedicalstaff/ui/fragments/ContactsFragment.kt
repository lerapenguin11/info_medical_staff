package com.example.infomedicalstaff.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.databinding.FragmentContactsBinding
import com.example.infomedicalstaff.ui.fragments.single.SingleChatFragment
import com.example.infomedicalstaff.utilits.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference

class ContactsFragment : Fragment() {
    private var _binding : FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter : FirebaseRecyclerAdapter<CommonModel, ContactHolder>
    private lateinit var mRefContacts : DatabaseReference
    private lateinit var mRefUser : DatabaseReference
    private lateinit var mReceivingUserListener : AppValueEventListener
    private var mapListener = hashMapOf<DatabaseReference, AppValueEventListener>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactsBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initFun()
    }

    private fun initFun(){
        initRecyclerView()
        //initButtonClickArron()
    }

    private fun initRecyclerView() {
        mRecyclerView = binding.rvContacts
        mRefContacts = REF_DATABASE_ROOT.child(NODE_USERS)

        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
            .setQuery(mRefContacts, CommonModel::class.java)
            .build()

        mAdapter = object : FirebaseRecyclerAdapter<CommonModel, ContactHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)

                return ContactHolder(view)
            }

            @SuppressLint("SuspiciousIndentation")
            override fun onBindViewHolder(
                holder: ContactHolder,
                position: Int,
                model: CommonModel
            ) {
                mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(model.id)

                mReceivingUserListener = AppValueEventListener {
                    val contact = it.getCommonModel()
                    holder.nameUser.text = contact.userName
                    holder.status.text = contact.state
                    holder.iconUser.setImageResource(R.drawable.user)
                    holder.constraintLayout.setOnClickListener {
                        val singleChatFragment = SingleChatFragment(contact)
                        val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.main_layout, singleChatFragment)
                        transaction.commit()
                    }
                }
                mRefUser.addValueEventListener(mReceivingUserListener)
                mapListener[mRefUser] = mReceivingUserListener



            }

        }
        mRecyclerView.setLayoutManager(LinearLayoutManager(context))
        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()
    }

    /*private fun initButtonClickArron(){
        binding.btArrowContacts.setOnClickListener{
            val chatListFragment = ChatsListFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.main_layout, chatListFragment)
            transaction.commit()
        }
    }*/

    class ContactHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameUser : TextView = view.findViewById(R.id.tv_name_user)
        val iconUser : ImageView = view.findViewById(R.id.iv_user)
        val status : TextView = view.findViewById(R.id.tv_user_appearance)
        val constraintLayout : ConstraintLayout = view.findViewById(R.id.constraintLayout_user)
    }

    override fun onPause() {
        super.onPause()
        mAdapter.stopListening()
        mapListener.forEach{
            it.key.removeEventListener(it.value)
        }
    }
}



