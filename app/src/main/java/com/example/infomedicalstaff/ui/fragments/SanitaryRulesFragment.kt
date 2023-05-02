package com.example.infomedicalstaff.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.databinding.FragmentSanitaryRulesBinding
import com.example.infomedicalstaff.view.adapter.CategoriesAdapter

class SanitaryRulesFragment : Fragment() {
    private var _binding : FragmentSanitaryRulesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSanitaryRulesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvSanRules.apply {
            adapter = CategoriesAdapter()
            binding.rvSanRules.layoutManager = LinearLayoutManager(context)
            binding.rvSanRules.adapter
        }
    }
}