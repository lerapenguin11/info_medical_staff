package com.example.infomedicalstaff.ui.fragments.single

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.business.model.UserModel
import com.example.infomedicalstaff.databinding.FragmentSingleChatBinding
import com.example.infomedicalstaff.ui.fragments.ChatsListFragment
import com.example.infomedicalstaff.utilits.*
import com.google.firebase.database.DatabaseReference


class SingleChatFragment(private val contact: CommonModel) : Fragment(){

    private var _binding : FragmentSingleChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var mListenerInfoToolbar : AppValueEventListener
    private lateinit var mReceivingUserModel : UserModel
    private lateinit var mRefUser : DatabaseReference

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
        initFun()

        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUserModel = it.getUserModel()
            initInfoToolbar()
        }

        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
    }

    @SuppressLint("ResourceType")
    private fun initInfoToolbar() {
        //binding.profileImage.setCircleBackgroundColorResource(R.drawable.user)
        binding.tvToolbarNameChat.text = mReceivingUserModel.userName
        binding.tvStateProfile.text = mReceivingUserModel.state
    }

    private fun initFun() {
        buttonClickArrow()
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

    override fun onPause() {
        super.onPause()
        buttonClickArrow()
    }
}