package com.example.infomedicalstaff.ui.fragments.groups

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.Key.VISIBILITY
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.databinding.FragmentAddContactsBinding
import com.example.infomedicalstaff.ui.fragments.chatList.ChatsListFragment
import com.example.infomedicalstaff.utilits.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView

class AddContactsFragment : Fragment() {
    private var _binding : FragmentAddContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
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
        mAdapter = AddContactsAdapter()

        mRefContacts.addListenerForSingleValueEvent(AppValueEventListener{
            mListItems = it.children.map { it.getCommonModel() }

            mListItems.forEach{model ->
                mRefUsers.child(model.id).addListenerForSingleValueEvent(AppValueEventListener{
                    if (model.fullName.isEmpty()) {
                        model.fullName = model.userName
                        model.state = model.state
                    }
                    mAdapter.updateListItem(model)
                })
            }
        })

        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter
       /* mRecyclerView = binding.rvAddContacts
        mAdapter = AddContactsAdapter()

        // 1 запрос
        mRefContacts.addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot ->
            mListItems = dataSnapshot.children.map { it.getCommonModel() }
            mListItems.forEach { model ->
                // 2 запрос
                mRefUsers.child(model.id)
                    .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot1 ->
                        val newModel = dataSnapshot1.getCommonModel()

                        // 3 запрос
                        mRefMessages.child(model.id).limitToLast(1)
                            .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot2 ->
                                val tempList = dataSnapshot2.children.map { it.getCommonModel() }

                                if (tempList.isEmpty()){
                                    newModel.lastMessage = "Чат очищен"
                                } else {
                                    newModel.lastMessage = tempList[0].text
                                }


                                if (newModel.fullName.isEmpty()) {
                                    newModel.fullName = newModel.userName
                                }
                                mAdapter.updateListItem(newModel)
                            })
                    })
            }
        })
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter*/
    }

    companion object{
        val listContacts = mutableListOf<CommonModel>()
    }
}



