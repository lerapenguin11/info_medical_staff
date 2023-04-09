package com.example.infomedicalstaff.ui.fragments.chatList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.databinding.FragmentChatsListBinding
import com.example.infomedicalstaff.ui.fragments.BaseFragment
import com.example.infomedicalstaff.ui.fragments.ContactsFragment
import com.example.infomedicalstaff.ui.fragments.HomeFragment
import com.example.infomedicalstaff.ui.fragments.groups.AddContactsFragment
import com.example.infomedicalstaff.utilits.*
import com.example.infomedicalstaff.view.adapter.ChatsListAdapter

class ChatsListFragment() : BaseFragment(R.layout.fragment_single_chat) {

    private var _binding : FragmentChatsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ChatsListAdapter
    private val mRefChatList = REF_DATABASE_ROOT.child(NODE_CHAT_LIST).child(CURRENT_UID)
    private val mRefUser = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGE).child(CURRENT_UID)
    private var mListItems = listOf<CommonModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatsListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
        createChat()
        initButtonClickArrow()
    }
    private fun initRecyclerView() {
        mRecyclerView = binding.rvChats
        mAdapter = ChatsListAdapter()

        mRefChatList.addListenerForSingleValueEvent(AppValueEventListener{
            mListItems = it.children.map { it.getCommonModel() }
            mListItems.forEach{model ->
                mRefUser.child(model.id).addListenerForSingleValueEvent(AppValueEventListener{
                    val newModel = it.getCommonModel()
                    mRefMessages.child(model.id).limitToLast(1)
                        .addListenerForSingleValueEvent(AppValueEventListener{
                            val tempList = it.children.map { it.getCommonModel() }
                            newModel.lastMessage = tempList[0].text
                            mAdapter.updateListItem(newModel)
                        })
                })
            }
        })

        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter
    }

    private fun initButtonClickArrow() {
        binding.btArrowChatList.setOnClickListener{
            val homeFragment = HomeFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.main_layout, homeFragment)
            transaction.commit()
        }
    }

    private fun createChat(){
        /*binding.button.setOnClickListener{
            val contactsFragment = ContactsFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.main_layout, contactsFragment)
            transaction.commit()
        }*/
        binding.button.setOnClickListener{
            val addContactsFragment = AddContactsFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.main_layout, addContactsFragment)
            transaction.commit()
        }
    }


}