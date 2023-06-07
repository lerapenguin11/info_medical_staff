package com.example.infomedicalstaff.ui.fragments.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.databinding.FragmentCreateGroupChatBinding
import com.example.infomedicalstaff.ui.fragments.HomeFragment
import com.example.infomedicalstaff.ui.fragments.chatList.ChatsListFragment
import com.example.infomedicalstaff.utilits.createGroupDatabase
import com.example.infomedicalstaff.utilits.replaceFragment

class CreateGroupChatFragment(private var listContacts : List<CommonModel>): Fragment() {
    private var _binding: FragmentCreateGroupChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter : AddContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateGroupChatBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
        binding.fbCreateGroup.setOnClickListener{
            AddContactsFragment.listContacts.forEach {
                val nameGroup = binding.etNameGroup.text.toString()
                if(nameGroup.isEmpty()){
                    Toast.makeText(context, "Введите сообщение", Toast.LENGTH_LONG).show()
                } else {
                    createGroupDatabase(nameGroup, listContacts,/* photoGroup*/){
                        replaceFragment(ChatsListFragment())
                    }
                }
                println(it.id)
                replaceFragment(CreateGroupChatFragment(AddContactsFragment.listContacts))
            }
        }
        binding.etNameGroup.requestFocus()

        binding.btArrowContacts.setOnClickListener { replaceFragment(HomeFragment()) }
    }


    private fun initRecyclerView() {
        mRecyclerView = binding.rvUserGroup
        mAdapter = AddContactsAdapter(listContacts)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter

        //listContacts.forEach {mAdapter.updateListItem(it)}
    }
}