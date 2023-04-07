package com.example.infomedicalstaff.ui.fragments.single

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.business.model.UserModel
import com.example.infomedicalstaff.databinding.FragmentSingleChatBinding
import com.example.infomedicalstaff.ui.fragments.ChatsListFragment
import com.example.infomedicalstaff.utilits.*
import com.example.infomedicalstaff.view.adapter.SingleChatAdapter
import com.google.firebase.database.DatabaseReference

class SingleChatFragment(private val contact: CommonModel) : Fragment(){

    private var _binding : FragmentSingleChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var mListenerInfoToolbar : AppValueEventListener
    private lateinit var mReceivingUserModel : UserModel
    private lateinit var mRefUser : DatabaseReference
    private lateinit var mRefMessage : DatabaseReference
    private lateinit var mAdapter : SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessageListener : AppValueEventListener
    private var mListMessages = emptyList<CommonModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSingleChatBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initInfoToolbar()
        initRecyclerView()
        buttonClickArrow()
        buttonClickMessage()

    }

    @SuppressLint("ResourceType")
    private fun initInfoToolbar() {
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUserModel = it.getUserModel()
            binding.tvToolbarNameChat.text = mReceivingUserModel.userName
            binding.tvStateProfile.text = mReceivingUserModel.state
            //binding.profileImage.setCircleBackgroundColorResource(R.drawable.user)
        }

        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
    }

    private fun initRecyclerView() {
        mRecyclerView = binding.rvSingleChat
        mAdapter = SingleChatAdapter()
        mRefMessage = REF_DATABASE_ROOT.child(NODE_MESSAGE)
            .child(CURRENT_UID)
            .child(contact.id)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter
        mMessageListener = AppValueEventListener { dataSnapshot ->
            mListMessages = dataSnapshot.children.map { it.getCommonModel() }
            mAdapter.setList(mListMessages)
            mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
        }

        mRefMessage.addValueEventListener(mMessageListener)
    }

    private fun buttonClickArrow() {
        binding.btArrowChatList.setOnClickListener{
            val chatListFragment = ChatsListFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.main_layout, chatListFragment)
            transaction.commit()

            //mRefUser.removeEventListener(mListenerInfoToolbar)
        }
    }

    private fun buttonClickMessage(){
        binding.btPlaneMessageSingleChat.setOnClickListener {
            val inputMessage = binding.etInputMessage
            val  message = inputMessage.text.toString()
            if(message.isEmpty()){
                Toast.makeText(context, "Введите сообщение", Toast.LENGTH_LONG).show()
            } else sendMessage(message, contact.id, TYPE_TEXT){
                inputMessage.setText("")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        buttonClickArrow()
        mRefMessage.removeEventListener(mMessageListener)
    }
}