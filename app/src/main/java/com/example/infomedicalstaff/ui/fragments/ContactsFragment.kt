package com.example.infomedicalstaff.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.databinding.FragmentContactsBinding
import com.example.infomedicalstaff.utilits.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class ContactsFragment : Fragment() {
    private var _binding : FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter : FirebaseRecyclerAdapter<CommonModel, ContactHolder>
    private lateinit var mRefContacts : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactsBinding.inflate(inflater, container, false)

        /*initContacts()*/
        initRecyclerView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
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

            override fun onBindViewHolder(
                holder: ContactHolder,
                position: Int,
                model: CommonModel
            ) {

                var st = ""
                    if( REF_DATABASE_ROOT.child(NODE_USERS).child(CHILD_FULL_NAME).toString().isEmpty()){
                        holder.nameUser.text = model.fullName
                    }else holder.nameUser.text = model.userName




                holder.status.text = model.state
                holder.iconUser.setImageResource(R.drawable.user)
            }

        }
        mRecyclerView.setLayoutManager(LinearLayoutManager(context))
        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()
    }

    class ContactHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameUser : TextView = view.findViewById(R.id.tv_name_user)
        val iconUser : ImageView = view.findViewById(R.id.iv_user)
        val status : TextView = view.findViewById(R.id.tv_user_appearance)

    }

    override fun onPause() {
        super.onPause()
        mAdapter.stopListening()
    }
}



