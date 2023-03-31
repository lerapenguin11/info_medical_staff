package com.example.infomedicalstaff.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.User
import com.example.infomedicalstaff.databinding.FragmentProfileBinding
import com.example.infomedicalstaff.utilits.USER

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initFunc()
        addDataProfileDatabase()
    }

    private fun initFunc() {
        createChangeNameFragment()
    }

    private fun createChangeNameFragment() {
        binding.etProfileUserName.setOnClickListener {
            val changeNameFragment = ChangeNameFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.main_layout, changeNameFragment)
            transaction.commit()
        }
    }

    private fun addDataProfileDatabase(){
        binding.tvNameProfile.setText(USER.fullName)
        binding.etProfileEmail.setText(USER.email)
        binding.etProfileUserName.setText(USER.fullName)
    }
}