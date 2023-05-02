package com.example.infomedicalstaff.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.databinding.FragmentHomeBinding
import com.example.infomedicalstaff.ui.fragments.chatList.ChatsListFragment
import com.example.infomedicalstaff.utilits.USER
import com.example.infomedicalstaff.utilits.replaceFragment

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.tvName.setText(USER.userName)

        binding.linearChats.setOnClickListener {
            val chatsListFragment = ChatsListFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.main_layout, chatsListFragment)
            transaction.commit()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initFunc()
    }

    private fun initFunc() {
        createProfileFragment()
        clickCategories()
    }

    private fun clickCategories() {
        binding.itemSanitaryRoles.setOnClickListener{
            replaceFragment(SanitaryRulesFragment())
        }
    }

    private fun createProfileFragment() {
        binding.ivHomeUserPhoto.setOnClickListener {
            val profileFragment = ProfileFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.main_layout, profileFragment)
            transaction.commit()
        }
    }
}