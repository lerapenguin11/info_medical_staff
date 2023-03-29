package com.example.infomedicalstaff.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.databinding.FragmentChatsListBinding
import com.example.infomedicalstaff.view.adapter.ChatsListAdapter

class ChatsListFragment : BaseFragment(R.layout.fragment_single_chat) {

    private var _binding : FragmentChatsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatsListBinding.inflate(inflater, container, false)

        binding.rvChats.apply {

                adapter = ChatsListAdapter()
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }
}