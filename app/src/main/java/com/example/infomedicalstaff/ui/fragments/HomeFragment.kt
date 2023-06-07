package com.example.infomedicalstaff.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.infomedicalstaff.databinding.FragmentHomeBinding
import com.example.infomedicalstaff.ui.fragments.chatList.ChatsListFragment
import com.example.infomedicalstaff.utilits.*
import com.google.firebase.database.*
import com.google.firebase.database.Transaction.Handler


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val username: DatabaseReference = REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child("userName")

        username.runTransaction(object : Handler {
            override fun doTransaction(@NonNull mutableData: MutableData): Transaction.Result {
                val name = mutableData.getValue(String::class.java)

                return Transaction.success(mutableData)
            }

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?
            ) {
                val username = currentData?.getValue(String::class.java)
                if (username != null) binding.tvName.setText(username)
            }
        })

        binding.linearChats.setOnClickListener {
           replaceFragment(ChatsListFragment())
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getdata()
    }

    private fun getdata() {


    }

    override fun onResume() {
        super.onResume()

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