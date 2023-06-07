package com.example.infomedicalstaff.ui.fragments.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.databinding.FragmentAddContactsBinding
import com.example.infomedicalstaff.ui.fragments.chatList.ChatsListFragment
import com.example.infomedicalstaff.utilits.*

class AddContactsFragment : Fragment() {
    private var _binding : FragmentAddContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefContacts = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGE).child(CURRENT_UID)
    private var mListItems = listOf<CommonModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddContactsBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }



    override fun onResume() {
        super.onResume()
        initRecyclerView()
        binding.button2Add.setOnClickListener{
            listContacts.forEach {
                println(it.id)
                replaceFragment(CreateGroupChatFragment(listContacts))
            }
        }

        initButtonClickArron()
    }
    private fun initButtonClickArron(){
        binding.btArrowAddContacts.setOnClickListener{
            val chatListFragment = ChatsListFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.main_layout, chatListFragment)
            transaction.commit()
        }
    }

    //TODO попровить
    private fun initRecyclerView() {

        mRecyclerView = binding.rvAddContacts


        // 1 запрос
        mRefContacts.addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot ->
            mListItems = dataSnapshot.children.map { it.getCommonModel() }
            mAdapter = AddContactsAdapter(mListItems)
            mRecyclerView.layoutManager = LinearLayoutManager(context)
            mRecyclerView.adapter = mAdapter
            //mAdapter.updateListItem(dataSnapshot.getCommonModel())
        })

    }

    companion object{
        val listContacts = mutableListOf<CommonModel>()
    }
}



