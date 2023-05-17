package com.example.infomedicalstaff.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.databinding.FragmentProfileBinding
import com.example.infomedicalstaff.utilits.*

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        addDataProfileDatabase()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initFunc()

    }

    private fun initFunc() {
        createChangeNameFragment()
        initButtonClickArrow()
    }

    private fun initButtonClickArrow() {
        binding.ivArrowProfile.setOnClickListener {
            replaceFragment(HomeFragment())
        }
    }

    private fun createChangeNameFragment() {
        binding.etProfileUserName.setOnClickListener {
            replaceFragment(ChangeNameFragment())
        }
    }

    private fun addDataProfileDatabase(){
        binding.tvNameProfile.setText(USER.userName)
        binding.etProfileEmail.setText(USER.email)
        binding.etProfileUserName.setText(USER.userName)
        binding.tvProfileStatus.setText(USER.state)
    }
}