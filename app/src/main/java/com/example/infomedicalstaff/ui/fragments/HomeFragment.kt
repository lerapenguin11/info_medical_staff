package com.example.infomedicalstaff.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.infomedicalstaff.databinding.FragmentHomeBinding
import com.example.infomedicalstaff.ui.fragments.chatList.ChatsListFragment
import com.example.infomedicalstaff.utilits.*

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)



        binding.linearChats.setOnClickListener {
           replaceFragment(ChatsListFragment())
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val userName = ""
        binding.tvName.text = USER.userName
        initFunc()
    }

    private fun initFunc() {
        createProfileFragment()
        clickCategories()
        clickNavigation()
    }

    private fun clickNavigation() {
        binding.navFav.setOnClickListener {
            replaceFragment(FavoriteFragment())
        }

        binding.navNewsFeed.setOnClickListener { replaceFragment(NewsFeedFragment()) }
    }

    private fun clickCategories() {
        binding.itemSanitaryRoles.setOnClickListener{
            replaceFragment(SanitaryRulesFragment())
        }
    }

    private fun createProfileFragment() {
        binding.ivHomeUserPhoto.setOnClickListener {
            replaceFragment(ProfileFragment())
        }
    }
}